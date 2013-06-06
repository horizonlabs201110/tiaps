package cn.npnt.tiaps.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.npnt.tiaps.assemble.vo.LookDataBody;
import cn.npnt.tiaps.assemble.vo.SingleLookBody;
import cn.npnt.tiaps.base.util.WorkerThreadPoolManager;
import cn.npnt.tiaps.base.vo.BaseResponseVO;
import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.constants.Constants;
import cn.npnt.tiaps.entity.Friendship;
import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.Photo;
import cn.npnt.tiaps.entity.ShareToSns;
import cn.npnt.tiaps.entity.User;
import cn.npnt.tiaps.entity.WearBrand;
import cn.npnt.tiaps.service.FriendshipService;
import cn.npnt.tiaps.service.LookService;
import cn.npnt.tiaps.service.ShareToSnsService;
import cn.npnt.tiaps.service.TimelineService;
import cn.npnt.tiaps.service.UserService;
import cn.npnt.tiaps.util.DateUtil;
import cn.npnt.tiaps.util.FileUploadUtil;
import cn.npnt.tiaps.vo.LookVO;
import cn.npnt.tiaps.workSchedule.DeleteLookWorker;
import cn.npnt.tiaps.workSchedule.LookSendTimeline;

import com.google.gson.Gson;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2011 2011-12-21 下午5:46:46
 * @description Look（微博）前端请求处理类 
 */
@Controller
public class LookController {

	private static final Logger logger = Logger.getLogger(LookController.class);
	
	private Gson gson = new Gson();
	private LookService lookService;
	private UserService userService;
	private LookSendTimeline lookSendTimelineWorker;
	private DeleteLookWorker deleteLookWorker;
	private ShareToSnsService shareToSnsService;
	private FriendshipService friendshipService;
	private TimelineService timelineService;
	
	@Autowired
	public void setLookService(LookService lookService){
		this.lookService = lookService;
	}
	@Autowired
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	@Autowired
	public void setLookSendTimelineWorker(LookSendTimeline worker){
		this.lookSendTimelineWorker = worker;
	}
	
	@Autowired
	public void setDeleteLookWorker(DeleteLookWorker deleteLookWorker){
		this.deleteLookWorker = deleteLookWorker;
	}
	@Autowired
	public void setShareToSnsService(ShareToSnsService shareToSnsService){
		this.shareToSnsService = shareToSnsService;
	}
	@Autowired
	public void setFriendshipService(FriendshipService friendshipService){
		this.friendshipService = friendshipService;
	}
	@Autowired
	public void setTimelineService(TimelineService timelineService){
		this.timelineService = timelineService;
	}
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-22 下午11:03:03
	 * @description 创建look请求处理方法
	 */
	@RequestMapping(value = "/look/create_look", method = RequestMethod.POST
//			, 
//			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void createLook(HttpServletRequest request,HttpServletResponse response){
		FileUploadUtil uploadUtil = new FileUploadUtil();
		Map<String, Object> params = null;
		int ret = 1;//缺省系统错误异常
		BaseResponseVO responseVO = null;
		try {
			params =  uploadUtil.scaleImage(request);
			String accessId = request.getHeader("accessId");
//			String accessId = (String)params.get("accessId");//暂时创建look时候用浏览器测试，如果用客户端测试请用上一行代码
			User user = userService.getUserByAccessId(accessId);
			
			String lookString = (String)params.get("look");
			JSONObject lookObj = JSONObject.fromString(lookString);
			String content = lookObj.getString("content");//微博内容
			Integer occasionId = lookObj.getInt("occasionId");//场景id
			List<FileItem> imageItems = (List<FileItem>)params.get("imageItems");
			responseVO = new BaseResponseVO();
			StringBuilder searchText = new StringBuilder();
			if(imageItems != null && imageItems.size() > 0){
				Date current = new Date();
				WearBrand brand = null;//品牌对象
				Photo photo = null;
				Set<WearBrand> brandList = null;//装载一个look里面所有的品牌对象
				Look look = new Look();
				Set<Photo> photoes = new HashSet<Photo>();
				JSONArray array = lookObj.getJSONArray("photos");//品牌集合json
				for(int i=0;i < array.length();i++){
					photo = new Photo();
					photo.setDeleted(false);
					photo.setVersion(1);
					photo.setCreateTime(current);
					photo.setVistedCount(0);
					brandList = new HashSet<WearBrand>();
					JSONObject JsonObj = array.getJSONObject(i);
					photo.setIndex((byte) i);//photo坐标
					JSONArray brandsArr = JsonObj.getJSONArray("clothingBrands");
					for(int j=0;j<brandsArr.length();j++){
						JSONObject JSONBrand = brandsArr.getJSONObject(j);//品牌json对象
						brand = new WearBrand();
						brand.setBrand(JSONBrand.getString("brand"));
						brand.setClothing(JSONBrand.getString("clothing"));
						brand.setPhoto(photo);
						brandList.add(brand);
						searchText.append("<.").append(brand.getBrand()).append(".>");
					}
					photo.setWearBrands(brandList);
					photo.setLook(look);
					photo.setPicUrl("/no.jpg");
					photoes.add(photo);
				}
				look.setUser(user);
				look.setPhotos(photoes);
				look.setOccasionId(occasionId.longValue());
				look.setText(content);//后面还需要解析@给某一些用户
				searchText.append(content);
				searchText.append(" <").append(user.getNickname()).append("> ");
				look.setSearchText(searchText.toString());
				String uploadPath = request.getServletContext().getRealPath("/")+"images"+File.separator + "looks";
				String writeURL = Constants.PHOTO_LOOK_URL;
				lookService.createLook(look,imageItems,uploadPath,writeURL);
				//组装返回值
				QueryParamVO param = new QueryParamVO();
				List<Look> lookList = lookService.getUserLooks(user, param);
				//将look转换为LookVO
				List<LookVO> voList = null;
				if(lookList != null){
					voList = new ArrayList<LookVO>();
					for(Look pojo : lookList){
						voList.add(lookService.convertToVO(pojo,user));
					}
				}
				LookDataBody lookDataBody = new LookDataBody();
				lookDataBody.setLooks(voList);
				responseVO.setBody(lookDataBody);
				ret = 0;
				//维护timeline关系数据
				try {
					lookSendTimelineWorker.setLook(look);
					lookSendTimelineWorker.setUser(user);
					WorkerThreadPoolManager wtpm = WorkerThreadPoolManager.getInstance();
					wtpm.executeWorker(lookSendTimelineWorker);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				ret = 2;//请求参数错误，参数项少了
			}
			responseVO.getHeader().setRet(ret);
		} catch (Exception e) {
			e.printStackTrace();
			responseVO = new BaseResponseVO(0, e);
		}
		String userJson = gson.toJson(responseVO);
		try {
			response.getWriter().print(userJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-25 上午8:56:03
	 * @description 删除look
	 */
	@RequestMapping(value = "/look/delete_look", method = RequestMethod.POST, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void deleteLook(HttpServletRequest request,HttpServletResponse response){
		int ret = 1;
		String accessId = request.getHeader("accessId");
		String lookId = request.getParameter("lookId");
		if(StringUtils.isNotBlank(accessId) && StringUtils.isNotBlank(lookId)){
			User user = userService.getUserByAccessId(accessId);
			long id = Long.parseLong(lookId);
			try {
				if(user != null){
					Look look = lookService.getLookByPK(id);
					if(look != null){
						if(look.getUser().getId() == user.getId()){
							look = lookService.deleteLook(look);
							if(look != null){
								try {
									deleteLookWorker.setLook(look);
									deleteLookWorker.setUser(user);
									WorkerThreadPoolManager wtpm = WorkerThreadPoolManager.getInstance();
									wtpm.executeWorker(deleteLookWorker);
								} catch (Exception e) {
									e.printStackTrace();
								}
								ret = 0;//删除look success
							}
						}else{
							ret = 21;
						}
					}else{
						ret = 20;
					}
				}else{
					ret = 11;
				}
			} catch (Exception e) {
				e.printStackTrace();
				String mess = gson.toJson(new BaseResponseVO(1,e));
				try {
					response.getWriter().print(mess);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return;
			}
		}else{
			ret = 2;
		}
		BaseResponseVO responseVO = new BaseResponseVO();
		responseVO.getHeader().setRet(ret);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-25 下午6:02:37
	 * @description 获取用户自己原创的和转发的微博列表  @我的SNS@
	 */
	@RequestMapping(value = "/look/timeline_user", method = RequestMethod.GET, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void timelineUser(HttpServletRequest request,HttpServletResponse response){
		BaseResponseVO responseVO = null;
		int ret = 1;
		String userId = request.getParameter("userId");//用户id
		String countStr = request.getParameter("count");//返回look的个数
		String pageStr = request.getParameter("page");//返回结果的页码
		String sinceId = request.getParameter("sinceId");
		try{
			QueryParamVO param = new QueryParamVO();
			User user = null;
			if(StringUtils.isBlank(userId)){
				throw new NumberFormatException();
			}else{
				param.userId = Long.parseLong(userId);
				user = new User();
				user.setId(param.userId);
			}
			if(StringUtils.isNotBlank(countStr)){
				param.pageSize = Integer.parseInt(countStr);
			}
			if(StringUtils.isNotBlank(pageStr)){
				param.pageNO = Integer.parseInt(pageStr);
			}
			if(StringUtils.isNotBlank(sinceId) && Long.parseLong(sinceId) > 0){
				param.sinceId = Long.parseLong(sinceId);
			}
			List<Look> lookList = lookService.getUserLooks(user, param);
//			//如果发现用户timeline为空的时候，则去查看该用户已经关注的人的微博，如果该用户没有任何关注的话，则还是给其看最新的微博
//			if(lookList == null || lookList.size() == 0){
//				QueryParamVO queryParam = new QueryParamVO();
//				queryParam.pageSize = 20;
//				queryParam.userId = user.getId();
//				List<Friendship> friendshipList = null;
//				List<Long> userIdList = new ArrayList<Long>();
//				Map<String,Object> lookQueryParam = new HashMap<String,Object>();
//				lookQueryParam.put("limit", 50);//多少条记录
//				lookQueryParam.put("sinceDate", DateUtil.getFormatedDateString(
//						DateUtil.getDecreaseDateString(new Date(), -7),
//						DateUtil.format_YYYY_MM_DD_HH_MM_SS));
//				do{
//					friendshipList = friendshipService.getFollowing(user, queryParam);
//					if(friendshipList != null && friendshipList.size() > 0){
//						for(Friendship ship : friendshipList){
//							userIdList.add(ship.getUserByFollowingId().getId());
//						}
//					}
//					queryParam.pageNO ++;
//				}while(friendshipList != null && friendshipList.size() == 20);
//				if(userIdList.size() > 0){
//					lookList = lookService.getUsersLook(userIdList, lookQueryParam);
//				}
//				if(lookList != null && lookList.size() > 0){
//					timelineService.addUserTimeline(lookList, user);
//				}else{//如果没有找到关注的人的微博，则还是选用之前的查询最新的一些微博记录
//					lookList = lookService.getLookByPage(param);
//				}
//			}
			List<LookVO> voList = null;
			//将look转换为LookVO
			if(lookList != null && lookList.size() > 0){
				voList = new ArrayList<LookVO>();
				for(Look pojo : lookList){
					voList.add(lookService.convertToVO(pojo,user));
				}
			}
			LookDataBody lookDataBody = new LookDataBody();
			lookDataBody.setLooks(voList);
			responseVO = new BaseResponseVO();
			responseVO.setBody(lookDataBody);
			ret = 0;
		}catch(NumberFormatException e){
			ret = 2;//参数错误
			responseVO = new BaseResponseVO(ret, e);
		}
		catch(Exception e){
			e.printStackTrace();
			responseVO = new BaseResponseVO(ret, e);
		}
		responseVO.getHeader().setRet(ret);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-28 上午10:24:46
	 * @description 随便逛逛列表，获取系统推荐的用户微博
	 */
	@RequestMapping(value = "/look/timeline_public", method = RequestMethod.GET, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void timelinePublic(HttpServletRequest request,HttpServletResponse response){
		int ret = 1;//缺省标示符为错误
		BaseResponseVO responseVO = null;
		String accessId = request.getParameter("accessId");
		String count = request.getParameter("count");//单页个数
		String page = request.getParameter("page");//页数
		String maxId = request.getParameter("maxId");//若指定此参数，则返回ID小于或等于maxId的微博，默认为0
		String sinceId = request.getParameter("sinceId");
		
		QueryParamVO param = new QueryParamVO();
		try {
			User user = null;
			if(StringUtils.isNotBlank(accessId)){
				user = userService.getUserByAccessId(accessId);
			}
			if(StringUtils.isNotBlank(count)){
				param.pageSize = Integer.parseInt(count);
			}
			if(StringUtils.isNotBlank(page)){
				param.pageNO = Integer.parseInt(page);
			}
			if(StringUtils.isNotBlank(sinceId) && Long.parseLong(sinceId) > 0){
				param.sinceId = Long.parseLong(sinceId);
			}
			if(StringUtils.isNotBlank(maxId) && Long.parseLong(maxId) > 0){
				param.maxId = Long.parseLong(maxId);
			}
			List<Look> recommends = lookService.getSystemRecommend(param);
			List<LookVO> voList = null;
			if(recommends != null && recommends.size() > 0){
				voList = new ArrayList<LookVO>();
				for(Look pojo : recommends){
					voList.add(lookService.convertToVO(pojo,user));
				}
			}
			responseVO = new BaseResponseVO();
			LookDataBody lookBody = new LookDataBody();
			lookBody.setLooks(voList);
			responseVO.setBody(lookBody);
			ret = 0;//响应正确
		}catch(NumberFormatException e){
			ret = 2;//参数发生错误
			responseVO = new BaseResponseVO(ret, e);
		}catch (Exception e) {
			e.printStackTrace();
			ret = 1;
			responseVO = new BaseResponseVO(ret, e);
		}
		responseVO.getHeader().setRet(ret);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-29 上午10:15:58
	 * @description 获取当前用户订阅的look   @首页@
	 * 获取符合以下条件的look,按照时间的顺序倒排，返回相应数量的look : 		
	 * 		a)	用户自己原创的或者转发的.b)	用户关注的人发的或者转发的 c)	add该用户的look
	 * 
	 *accessId	YES	string	该用户的accessId
	 *count	YES	int	返回look的个数
	 *page	YES	int	返回结果的页码，默认为1
	 *sinceId	NO	int	上次请求返回的look的最大的id，若指定此参数，则返回ID比sinceId大的微博（即比sinceId时间晚的look），默认为0
	 *maxId	NO	int	若指定此参数，则返回ID小于或等于maxId的微博，默认为0
	 */
	@RequestMapping(value = "/look/timeline_home", method = RequestMethod.GET, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void timelineHome(HttpServletRequest request,HttpServletResponse response){
		String accessId = request.getHeader("accessId");
		String couont = request.getParameter("count");
		String page = request.getParameter("page");
		String sinceId = request.getParameter("sinceId");
		String maxId = request.getParameter("maxId");
		int ret = 0;//状态码标示符
		BaseResponseVO responseVO = null;
		try{
			logger.info("timeline home loading");
			if(StringUtils.isNotBlank(accessId) && StringUtils.isNotBlank(couont)){
				User user = userService.getUserByAccessId(accessId);
				QueryParamVO param = new QueryParamVO();
				//因为maxId和sinceId的缘故，所以page对象是可以为空
				if(StringUtils.isNotBlank(page)){
					param.pageNO = Integer.parseInt(page);
				}else{
					param.pageNO = null;
				}
				param.pageSize = Integer.parseInt(couont);
				param.userId = user.getId();
				Map<String,Object> other = null;
				if(StringUtils.isNotBlank(sinceId)){
					if(other == null) other = new HashMap<String, Object>();
					other.put("sinceId", Long.parseLong(sinceId));
				}
				if(StringUtils.isNotBlank(maxId)){
					if(other == null) other = new HashMap<String, Object>();
					other.put("maxId", Long.parseLong(maxId));
				}
				List<Look> lookList = lookService.getTimeLineHome(param, other);
				//如果发现用户timeline为空的时候，则去查看该用户已经关注的人的微博，如果该用户没有任何关注的话，则还是给其看最新的微博
				if(StringUtils.isBlank(sinceId) && StringUtils.isNotBlank(maxId)
						&& (lookList == null || lookList.size() == 0)
						){
					QueryParamVO queryParam = new QueryParamVO();
					queryParam.pageSize = 20;
					queryParam.userId = user.getId();
					List<Friendship> friendshipList = null;
					List<Long> userIdList = new ArrayList<Long>();
					Map<String,Object> lookQueryParam = new HashMap<String,Object>();
					lookQueryParam.put("limit", 50);//多少条记录
					lookQueryParam.put("sinceDate", DateUtil.getFormatedDateString(
							DateUtil.getDecreaseDateString(new Date(), -7),
							DateUtil.format_YYYY_MM_DD_HH_MM_SS));
					do{
						friendshipList = friendshipService.getFollowing(user, queryParam);
						if(friendshipList != null && friendshipList.size() > 0){
							for(Friendship ship : friendshipList){
								userIdList.add(ship.getUserByFollowingId().getId());
							}
						}
						queryParam.pageNO ++;
					}while(friendshipList != null && friendshipList.size() == 20);
					if(userIdList.size() > 0){
						lookList = lookService.getUsersLook(userIdList, lookQueryParam);
					}
					if(lookList != null && lookList.size() > 0){
						timelineService.addUserTimeline(lookList, user);
					}else if(StringUtils.isNotBlank(sinceId) && StringUtils.isNotBlank(maxId)){//如果没有找到关注的人的微博，则还是选用之前的查询最新的一些微博记录
						lookList = lookService.getLookByPage(param);
					}
				}
				List<LookVO> voList = null;
				if(lookList != null && lookList.size() > 0){
					voList = new ArrayList<LookVO>();
					for(Look pojo : lookList){
						voList.add(lookService.convertToVO(pojo,user));
					}
				}
				LookDataBody lookBody = new LookDataBody();
				lookBody.setLooks(voList);
				responseVO = new BaseResponseVO();
				responseVO.setBody(lookBody);
				ret = 0;//响应正确
			}else{
				throw new IllegalArgumentException();//参数缺少了
			}
		}catch(NumberFormatException e){
			ret = 2;
			responseVO = new BaseResponseVO();
		}catch(IllegalArgumentException e){
			ret = 2;
			responseVO = new BaseResponseVO();
		}catch(Exception e){
			e.printStackTrace();
			ret = 1;
			responseVO = new BaseResponseVO(ret, e);
		}
		responseVO.getHeader().setRet(ret);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-1 上午10:00:48
	 * @description 转发look请求处理
	 * 
	 * 在站内转发一个look用户输入的140个子中包含@（提到的）和#主题。服务器端要对内容进行解析，将该look@的用户和主题保存到数据库
	 */
	@RequestMapping(value = "/look/repost_look", method = RequestMethod.POST, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void repostLook(HttpServletRequest request,HttpServletResponse response){
		String accessId = request.getHeader("accessId");
		String lookIdStr = request.getParameter("lookId");
		String text = request.getParameter("text");
		int ret = 0;//状态码标示符
		BaseResponseVO responseVO = new BaseResponseVO();
		try{
			if(StringUtils.isNotBlank(accessId) && StringUtils.isNotBlank(lookIdStr)){
				User user = userService.getUserByAccessId(accessId);
				if(user == null){
					ret = 5;//未登录注册的用户
				}else{
					Look look = lookService.getLookByPK(Long.parseLong(lookIdStr));
					if(look == null){//look doesn't exist
						ret = 20;
					}else{
						Look reporterLook = new Look();
						reporterLook.setUser(user);
						reporterLook.setText(text);
						reporterLook.setRepostType((byte)0);//普通转发
						reporterLook = lookService.repostLook(look, reporterLook);
						//维护timeline关系数据
						try {
							lookSendTimelineWorker.setLook(reporterLook);
							lookSendTimelineWorker.setUser(user);
							WorkerThreadPoolManager wtpm = WorkerThreadPoolManager.getInstance();
							wtpm.executeWorker(lookSendTimelineWorker);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}else{
				throw new IllegalArgumentException();//参数不够
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
			ret = 2;
			responseVO = new BaseResponseVO(ret, e);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			ret = 2;
			responseVO = new BaseResponseVO(ret, e);
		}catch(Exception e){
			e.printStackTrace();
			ret = 1;
			responseVO = new BaseResponseVO(ret, e);
		}
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-3 下午3:29:22
	 * @description 转发第三放sns记录
	 */
	@RequestMapping(value = "/look/repost_thirdparty", method = RequestMethod.POST, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void repostThirdparty(HttpServletRequest request,HttpServletResponse response){
		String accessId = request.getHeader("accessId");
		String lookIdStr = request.getParameter("lookId");
		String text = request.getParameter("text");
		String snsTypeStr = request.getParameter("snsType");
		int ret = 0;//状态码标示符
		BaseResponseVO responseVO = new BaseResponseVO();
		try{
			if(StringUtils.isNotBlank(accessId) && StringUtils.isNotBlank(lookIdStr)){
				User user = userService.getUserByAccessId(accessId);
				if(user == null){
					ret = 5;//未登录注册的用户
				}else{
					Look look = lookService.getLookByPK(Long.parseLong(lookIdStr));
					if(look == null){//look doesn't exist
						ret = 20;
					}else{
						byte snsType = Byte.parseByte(snsTypeStr);
						ShareToSns share = new ShareToSns();
						share.setCreateTime(new Date());
						share.setLook(look);
						share.setSnsType(snsType);
						share.setText(text);
						share.setUser(user);
						share.setVersion(0);
						shareToSnsService.reportToThirdParty(look , share);
						ret = 0;
					}
				}
			}else{
				throw new IllegalArgumentException();//参数不够
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
			ret = 2;
			responseVO = new BaseResponseVO(ret, e);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			ret = 2;
			responseVO = new BaseResponseVO(ret, e);
		}catch(Exception e){
			e.printStackTrace();
			ret = 1;
			responseVO = new BaseResponseVO(ret, e);
		}
		responseVO.getHeader().setRet(ret);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-8 下午5:35:57
	 * @description 通过lookId查找到对应look对象信息
	 */
	@RequestMapping(value = "/look/get_look", method = RequestMethod.GET, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void getLookById(HttpServletRequest request,HttpServletResponse response){
		int ret = 5;
		BaseResponseVO responseVO = new BaseResponseVO();
		if(request.getAttribute("user") != null){
			try{
				User user = (User)request.getAttribute("user");
				long lookId = Long.parseLong(request.getParameter("lookId"));
				Look look = lookService.getLookByPK(lookId);
				if(look != null){
					LookVO vo = lookService.convertToVO(look, user);
					SingleLookBody lookBody = new SingleLookBody();
					lookBody.setLook(vo);
					responseVO.setBody(lookBody);
					ret = 0;
				}else{
					ret = 20;
				}
			}catch(NumberFormatException e){
				ret = 2;
			}catch(Exception e){
				e.printStackTrace();
				ret = 1;
				responseVO = new BaseResponseVO(ret, e);
			}
		}
		responseVO.getHeader().setRet(ret);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-8 下午5:37:39
	 * @description 根据搜索条件搜索look
	 * 
	 * 性别、品牌、场景、主题、内容搜索look
	 */
	@RequestMapping(value = "/look/timeline_search", method = RequestMethod.GET, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void timelineSearch(HttpServletRequest request,HttpServletResponse response){
		int ret = 5;
		BaseResponseVO responseVO = new BaseResponseVO();
		if(request.getAttribute("user") != null){
			try{
				User user = (User)request.getAttribute("user");
				String countStr = request.getParameter("count");
				String pageStr = request.getParameter("page");
				String genderStr = request.getParameter("gender");
				String text = request.getParameter("text");
				if(text!=null)
					text=new String(text.getBytes("iso-8859-1"), "UTF-8");
				String occationStr = request.getParameter("occasionId");
				if(StringUtils.isNotBlank(pageStr) && StringUtils.isNotBlank(countStr)){
					Map<String,Object> param = new HashMap<String, Object>();
					Integer gender = null;
					if(StringUtils.isNotBlank(genderStr)){
						gender = Integer.parseInt(genderStr);
						param.put("gender", gender);
					}
					Long occasionId = null;
					if(StringUtils.isNotBlank(occationStr)){
						occasionId = Long.parseLong(occationStr);
						param.put("occasionId", occasionId);
					}
					if(StringUtils.isNotBlank(text)){
						param.put("text", text);
					}
					QueryParamVO queryParam = new QueryParamVO();
					if(StringUtils.isNotBlank(countStr)){
						queryParam.pageSize = Integer.parseInt(countStr);
					}
					if(StringUtils.isNotBlank(pageStr)){
						queryParam.pageNO = Integer.parseInt(pageStr);
					}
					List<Look> lookList = lookService.getSearchLook(queryParam, param);
					List<LookVO> voList = null;
					if(lookList != null && lookList.size() > 0){
						voList = new ArrayList<LookVO>();
						for(Look pojo : lookList){
							voList.add(lookService.convertToVO(pojo,user));
						}
					}
					LookDataBody lookBody = new LookDataBody();
					lookBody.setLooks(voList);
					responseVO = new BaseResponseVO();
					responseVO.setBody(lookBody);
					ret = 0;//响应正确
				}else{
					ret = 2;
				}
			}catch(IllegalArgumentException e){
				e.printStackTrace();
				ret = 2;
				responseVO = new BaseResponseVO(ret, e);
			}catch(Exception e){
				e.printStackTrace();
				ret = 1;
				responseVO = new BaseResponseVO(ret, e);
			}
		}
		responseVO.getHeader().setRet(ret);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
