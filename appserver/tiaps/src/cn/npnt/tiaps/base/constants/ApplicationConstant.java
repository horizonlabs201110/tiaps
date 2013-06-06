package cn.npnt.tiaps.base.constants;

/**
 * 
 * @author lijia 2011-7-13
 * 
 */
public class ApplicationConstant {
	/************* XML Declare ******************/
	public final static String DECLARE_STRING ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	/************* ContextType ******************/
	public final static String CONTEXTTYPE_XML = "application/xml";
	public final static String CONTEXTTYPE_JSON = "application/json";	
	
	/***** message code *****/
	/** 提示信息编码由1位字母和2位数字组成，第一位为错误类型 **/
	/** 错误类型有 ： S=成功信息 F=失败信息 R=资源信息 V=验证信息 I=系统内部信息 */
	/** 成功 **/
	public final static String SUCCESS = "success";	
	/** 异常 **/
	public final static String ERROR = "error";	
	/** 请求不是多部分内容 **/
	public final static String NO_MULTIPART_CONTENT = "Request is not multipartContent"; 
	/** 资源暂停使用 **/
	public final static String MESSAGE_CODE_UNUSABLE_FAIL = "R01";
	/** 资源没有权限 **/
	public final static String MESSAGE_CODE_UNAVAILABLE_FAIL = "R02";
	/** schema验证错误 **/
	public final static String MESSAGE_CODE_XMLVALIDATE_FAIL = "V01";
	/** 用户不存在或身份标识错误 **/
	public final static String MESSAGE_CODE_USERKYE_FAIL = "V02";
	/** 版本信息错误 **/
	public final static String MESSAGE_CODE_VERSION_FAIL = "V03";
	/** 系统内部错误 **/
	public final static String MESSAGE_CODE_SYSTEMERROR_FAIL = "I00";

	/** 接口正确响应结果标示**/
	public static final int RET_SUCCESS = 0;
	/** 是否是debug模式发布**/
	public static final boolean IS_PUBLISHED_DEBUG = true;
	
}
