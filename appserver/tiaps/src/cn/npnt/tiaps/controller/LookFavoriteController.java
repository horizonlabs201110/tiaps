package cn.npnt.tiaps.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.npnt.tiaps.assemble.vo.LookDataBody;
import cn.npnt.tiaps.base.vo.BaseResponseVO;
import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.constants.Constants;
import cn.npnt.tiaps.entity.Favorite;
import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.User;
import cn.npnt.tiaps.service.FavoriteService;
import cn.npnt.tiaps.service.LookService;
import cn.npnt.tiaps.service.UserService;
import cn.npnt.tiaps.vo.FavoriteVO;
import cn.npnt.tiaps.vo.LookVO;

import com.google.gson.Gson;

@Controller
public class LookFavoriteController {

	private Gson gson = new Gson();
	private UserService userService;
	private LookService lookService;
	private FavoriteService favoriteService;
	
	@Autowired
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	@Autowired
	public void setLookService(LookService lookService){
		this.lookService = lookService;
	}
	@Autowired
	public void setFavoriteService(FavoriteService favoriteService){
		this.favoriteService = favoriteService;
	}
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-29 下午6:10:10
	 * @description 收藏look
	 */
	@RequestMapping(value = "/lookFavorite/favorite_look", method = RequestMethod.POST, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 })
	public void addFavoriteLook(HttpServletRequest request,HttpServletResponse response){
		int ret = 1;
		BaseResponseVO responseVO = new BaseResponseVO();
		String accessId = request.getHeader("accessId");
		if(StringUtils.isNotBlank(accessId)){
			try {
				User user = userService.getUserByAccessId(accessId);
				if(user != null){
					String lookId = request.getParameter("lookId");
					if(StringUtils.isNotBlank(lookId)){
						Look look = lookService.getLookByPK(Long.parseLong(lookId));
						Favorite favorite = null;
						if(look != null){
							favorite = favoriteService.addFavorite(user.getId(), look.getId());
							Look orginalLook = look;
							//如果不是原创的look时候，还需要给原创加上1
							if(look.getLookByOriginalId() != null){
								favoriteService.addFavorite(user.getId(), look.getLookByOriginalId().getId());
								orginalLook = lookService.getLookByPK(look.getLookByOriginalId().getId());
							}else{//是原创的话，将原创的收藏数量+1
								look.getLookStatistic().setFavoritedCount(look.getLookStatistic().getFavoritedCount() + 1);
							}
							if(favorite == null){//已经收藏过了
								ret = 25;
							}else{
								FavoriteVO vo = new FavoriteVO();
								vo.setId(look.getId());
								vo.setCount(orginalLook.getLookStatistic().getFavoritedCount().longValue());
								responseVO.setBody(vo);
								ret = 0;
							}
						}else{
							ret = 20;//look doesn't exist
						}
					}else{
						ret = 2;
					}
				}else{
					ret = 5;
				}
			} catch(NumberFormatException e){
				ret =2;
				responseVO = new BaseResponseVO(ret, e);
			}catch (Exception e) {
				e.printStackTrace();
				ret =1;
				responseVO = new BaseResponseVO(ret, e);
			}
		}else{
			ret = 5;
		}
		responseVO.getHeader().setRet(ret);
		String userJson = gson.toJson(responseVO);
		try {
			response.getWriter().print(userJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/lookFavorite/unfavorite_look", method = RequestMethod.POST, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 })
	public void removeFavoriteLook(HttpServletRequest request,HttpServletResponse response){
		int ret = 1;
		BaseResponseVO responseVO = new BaseResponseVO();
		String accessId = request.getHeader("accessId");
		if(StringUtils.isNotBlank(accessId)){
			try {
				User user = userService.getUserByAccessId(accessId);
				if(user != null){
					String lookId = request.getParameter("lookId");
					if(StringUtils.isNotBlank(lookId)){
						Look look = lookService.getLookByPK(Long.parseLong(lookId));
						if(look != null){
							Favorite param = new Favorite();
							param.setLook(look);
							param.setUser(user);
							List<Favorite> favoriteList = favoriteService.getFavoritesByCondition(param);
							if(favoriteList != null && favoriteList.size() == 1){
								favoriteService.removeFavorite(favoriteList.get(0));
							}
							FavoriteVO vo = new FavoriteVO();
							vo.setId(look.getId());
							vo.setCount(favoriteService.getLookFavoriteNum(look.getId()));
							responseVO.setBody(vo);
							ret = 0;
						}else{
							ret = 20;//look doesn't exist
						}
					}else{
						ret = 2;
					}
				}else{
					ret = 5;
				}
			} catch(NumberFormatException e){
				ret =2;
				responseVO = new BaseResponseVO(ret, e);
			}catch (Exception e) {
				e.printStackTrace();
				ret =1;
				responseVO = new BaseResponseVO(ret, e);
			}
		}else{
			ret = 5;
		}
		responseVO.getHeader().setRet(ret);
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
	 * @createDate 2011 2011-12-30 下午10:41:40
	 * @description 查询收藏列表
	 */
	@RequestMapping(value = "/lookFavorite/timeline_favorite", method = RequestMethod.GET, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 })
	public void timelinefavorite(HttpServletRequest request,HttpServletResponse response){
		int ret = 1;
		BaseResponseVO responseVO = new BaseResponseVO();
		String accessId = request.getHeader("accessId");
		String userIdStr = request.getParameter("userId");
		String countStr = request.getParameter("count");
		String pageStr = request.getParameter("page");
		String sinceId = request.getParameter("sinceId");
		if(StringUtils.isNotBlank(userIdStr) &&
				StringUtils.isNotBlank(countStr) &&
				StringUtils.isNotBlank(pageStr)){
				try{
					User user = userService.getByPK(Long.parseLong(userIdStr));
					if(user == null){
						throw new NullPointerException();
					}
					QueryParamVO param = new QueryParamVO();
					param.userId = Long.parseLong(userIdStr);
					param.pageNO = Integer.parseInt(pageStr);
					param.pageSize = Integer.parseInt(countStr);
					if(StringUtils.isNotBlank(sinceId) && Long.parseLong(sinceId) > 0){
						param.sinceId = Long.parseLong(sinceId);
					}
					List<Look> lookList = favoriteService.getFavoriteByUser(param);
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
				}catch(NullPointerException e){
					ret = 2;
					responseVO = new BaseResponseVO(ret, e);
				}catch(NumberFormatException e){
					ret = 2;
					responseVO = new BaseResponseVO(ret, e);
				}catch(Exception e){
					e.printStackTrace();
					ret = 1;
					responseVO = new BaseResponseVO(ret, e);
				}
		}else{
			ret = 2;
		}
		String userJson = gson.toJson(responseVO);
		try {
			response.getWriter().print(userJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
