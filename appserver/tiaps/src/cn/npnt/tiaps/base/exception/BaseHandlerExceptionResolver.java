package cn.npnt.tiaps.base.exception;
///**
// * 
// */
//package com.newplusnew.tryIt.base.exception;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.newplusnew.tryIt.base.constants.ApplicationConstant;
//
///**
// * 异常处理 
// * 
// * @author lijia 2011-7-5
// * 
// */
//
//public class BaseHandlerExceptionResolver implements HandlerExceptionResolver {
//	private static final Logger _log = Logger.getLogger(BaseHandlerExceptionResolver.class);
//
//	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception) {
//		_log.error(exception);
//		try {
//			String contextType=request.getContentType();
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/xml");
//			if (exception instanceof TryItAPIException) {
//				//响应自定义错误信息
//				String code = ((TryItAPIException) exception).getCode();
//				Object[] obj=((TryItAPIException) exception).getObect();
//				response.getWriter().print(ApplicationConstant.getRespondMsg(contextType,"error", code, request.getHeader("version"),obj));
//			}else{
//				//其他错误信息，向客户端响应系统内部错误
//				if (exception instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
//					response.sendError(404);
//				}else{
//					_log.error("系统内部错误");
//					response.getWriter().print(ApplicationConstant.getRespondMsg(contextType,"error", ApplicationConstant.MESSAGE_CODE_SYSTEMERROR_FAIL, request.getHeader("version")));
//				}
//				
//			}
//		} catch (Exception e) {
//			_log.error(e);
//		}
//		return new ModelAndView();
//	}
//
//}
