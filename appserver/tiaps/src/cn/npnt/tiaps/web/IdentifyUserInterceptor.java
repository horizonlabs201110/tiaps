package cn.npnt.tiaps.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.npnt.tiaps.entity.User;
import cn.npnt.tiaps.service.UserService;

public class IdentifyUserInterceptor extends HandlerInterceptorAdapter {

	private UserService userService;
	private Logger logger = Logger.getLogger(IdentifyUserInterceptor.class);

	@Autowired
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
//		System.err.println("***************1");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
//		System.err.println("***************2");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		//拥有publicToken或者accessId合法的用户才能继续得到服务器处理
		System.err.println("***************0");
		String accessId = request.getHeader("accessId");
		String publicToken = request.getHeader("publicToken");
		if(StringUtils.isBlank(accessId)){
			accessId = request.getParameter("accessId");
		}
		if(StringUtils.isNotBlank(accessId) || StringUtils.isNotBlank(publicToken)){
			if(StringUtils.isNotBlank(accessId)){
				//invoke service to get a user ,if user is null,go on ,else
				User user = userService.getUserByAccessId(accessId);
				if(user != null){
					request.setAttribute("user", user);
					return true;
				}
			}
			if(StringUtils.isNotBlank(publicToken)){
				//check publicToken is valid or invalid,if valid,return true
				if(publicToken.equals("q3rger4hbw4t86ky")){//确认publicToken的取值规则
					return true;
				}
			}
		}
		response.getWriter().print("{'header': {'ret': 1502}}");
		return false;
	}

}
