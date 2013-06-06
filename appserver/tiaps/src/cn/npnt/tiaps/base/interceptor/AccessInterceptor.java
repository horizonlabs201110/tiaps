package cn.npnt.tiaps.base.interceptor;
//package com.newplusnew.tryIt.base.interceptor;
//
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * 拦截器 判断权限
// * 
// * @author 
// * 
// */
//public class AccessInterceptor extends HandlerInterceptorAdapter {
//	private static final Logger _log = Logger.getLogger(AccessInterceptor.class);
//	String contextType=null;
//	String description = null;// 错误提示
//	String version = null;
//	String uri = null;
//	String authenticate = null;
//	String method = null;
//	private IUserResourceService userResourceService;
//
//	@Autowired
//	public void setUserResourceService(IUserResourceService userResourceService) {
//		this.userResourceService = userResourceService;
//	}
//
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/xml");
//		contextType=request.getContentType();
//		description = new String();
//		uri = request.getRequestURI();
//		version = request.getHeader("version");
//		authenticate = request.getHeader("authenticate");
//		method = request.getMethod();
//		_log.info("AccessInterceptor:preHandle  path: " + uri + " url:" + request.getRequestURL() + "  method:" + request.getMethod());
//		try {
//			_log.info("version:" + version);
//			_log.info("authenticate:" + authenticate);
//			// 版本号验证
//			ValidateVersion();
//			// 身份标识验证
//			ValidateAuthenticate();
//			if (!description.equals("")) {// 如果有错误信息，向客户端返回错误信息
//				response.getWriter().print(description);
//				return false;
//			}
//			return true;
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	/**
//	 * 验证身份 api权限
//	 * 
//	 * @param authenticate
//	 * @throws Exception
//	 */
//	private void ValidateAuthenticate() {
//		UserResourceVo vo = null;
//		boolean available = false;// 有效的
//		try {
//			vo = userResourceService.getUserResource(authenticate);// 等到用户信息和权限。如果报错，则表示用户不存在
//			// api权限验证
//			if (uri.indexOf(ApplicationConstant.API_URI_GET_TIME) == -1 || uri.indexOf(ApplicationConstant.API_URI_GET_PROVINCE) == -1
//					|| uri.indexOf(ApplicationConstant.API_URI_GET_CITY) == -1 || uri.indexOf(ApplicationConstant.API_URI_GET_COUNTY) == -1) {
//				List<ResourceVo> resourceList = vo.getResourceList();
//				if (resourceList != null) {
//					for (ResourceVo resourceVo : resourceList) {
//						if (uri.indexOf(resourceVo.getUri()) != -1 && resourceVo.getMethod().indexOf(method) != -1) {
//							available = true;
//							if (!resourceVo.getIsUsable()) {
//								// 资源停用
//								description = ApplicationConstant.getRespondMsg(contextType,"error", ApplicationConstant.MESSAGE_CODE_UNUSABLE_FAIL, version,
//										new Object[] { uri });
//							}
//						}
//					}
//				}
//				if (!available) {
//					// 没有资源权限
//					description = ApplicationConstant.getRespondMsg(contextType,"error", ApplicationConstant.MESSAGE_CODE_UNAVAILABLE_FAIL, version,
//							new Object[] { authenticate, uri, method });
//				}
//			}
//		} catch (Exception e) {
//			_log.error(e);
//			if (e instanceof EmsApiException) {
//				String code = ((EmsApiException) e).getCode();
//				Object[] obj = ((EmsApiException) e).getObect();
//				description = ApplicationConstant.getRespondMsg(contextType,"error", code, version, obj);
//			}
//
//		}
//	}
//
//	/**
//	 * 验证版本信息
//	 * 
//	 * @param version
//	 */
//	public void ValidateVersion() {
//		if (!ApplicationConstant.INTERNATIONAL_EUB_US_VERSION_1_0.equals(version)) {
//			description = ApplicationConstant.getRespondMsg(contextType,"error", ApplicationConstant.MESSAGE_CODE_VERSION_FAIL,
//					ApplicationConstant.INTERNATIONAL_EUB_US_VERSION_1_0);
//		}
//	}
//
//}
