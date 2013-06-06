package cn.npnt.tiaps.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.npnt.tiaps.assemble.vo.RemindReadBody;
import cn.npnt.tiaps.assemble.vo.RemindWriteBody;
import cn.npnt.tiaps.base.vo.BaseResponseVO;
import cn.npnt.tiaps.constants.Constants;
import cn.npnt.tiaps.entity.User;
import cn.npnt.tiaps.service.FriendshipService;
import cn.npnt.tiaps.service.TimelineService;

import com.google.gson.Gson;

@Controller
public class RemindController {

	private static final Logger logger = Logger.getLogger(RemindController.class);
	private Gson gson = new Gson();
	private FriendshipService friendshipService;
	private TimelineService timelineService;
	
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
	 * @createDate 2012 2012-4-5 下午2:00:32
	 * @description 查询某个人的未读取的信息
	 */
	@RequestMapping(value = "remind/unread_count", method = RequestMethod.GET, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
			)
	public void unreadInfo(HttpServletRequest request,HttpServletResponse response){
		BaseResponseVO responseVO = new BaseResponseVO();
		RemindReadBody remindBody = new RemindReadBody();
		Long userId = null;
		Long maxLookId = null;
		String userIdStr = request.getParameter("userId");
		String maxLookIdStr = request.getParameter("maxLookId");
		if(StringUtils.isNotBlank(userIdStr)){
			try{
				userId = Long.parseLong(userIdStr);
				//查找某个人的未读取的粉丝数量
				User userParam = new User();
				userParam.setInformed(false);
				userParam.setId(userId);
				Long unformedNum = friendshipService.getfollowerCount(userParam);
				remindBody.setFollower(unformedNum.intValue());
				if(StringUtils.isNotBlank(maxLookIdStr)){
					maxLookId = Long.parseLong(maxLookIdStr);
				}
				//查找某个人未读取的消息数量
				if(maxLookId != null){
					Map<String,Object> paramTL = new HashMap<String,Object>();
					paramTL.put("userId", userId);
					paramTL.put("minLookId", maxLookId);
					remindBody.setStatus(timelineService.getTimelineNum(paramTL));
				}else{
					remindBody.setStatus(0);
				}
			}catch(Exception e){
				logger.error(e.toString());
				responseVO = new BaseResponseVO(1,e);
			}
		}
		responseVO.getHeader().setRet(0);
		responseVO.setBody(remindBody);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-4-5 下午2:01:30
	 * @description 消息未读数清零接口说明：（目前只需要支持：关注我的人）（未读微博数量不需要清零）
	 */
	@RequestMapping(value = "remind/set_count", method = RequestMethod.POST, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
			)
	public void writeRemind(HttpServletRequest request,HttpServletResponse response){
		BaseResponseVO responseVO = new BaseResponseVO();
		RemindWriteBody remindBody = new RemindWriteBody();
		remindBody.setResult(false);
		int ret = 1;
		String userIdStr = request.getParameter("userId");
		String type = request.getParameter("type");//需要清零未读数的消息项。follower：新粉丝数目前只需支持这一项，类型以后会随需求增加而增加。注意：一次请求只能操作一项。
		if(StringUtils.isNotBlank(userIdStr) && StringUtils.isNotBlank(type)){
			try{
				Long userId = Long.parseLong(userIdStr);
				if(type.equals("follower")){//清理那些未阅读的关注人
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("followingId", userId);
					friendshipService.doInformed(param);
					remindBody.setResult(true);
					remindBody.setType("follower");
				}
				ret = 0;
			}catch(Exception e){
				logger.error(e.toString());
				responseVO = new BaseResponseVO(1,e);
			}
		}
		responseVO.getHeader().setRet(ret);
		responseVO.setBody(remindBody);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
