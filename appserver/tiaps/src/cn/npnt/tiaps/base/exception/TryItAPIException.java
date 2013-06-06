package cn.npnt.tiaps.base.exception;
/**
 * 
 * @author lijia 2011-5-18
 * 
 */
public class TryItAPIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//错误代码
	private String code;
	//错误信息描述
	private String descrption;
	
	public TryItAPIException(String code,String descrption){
		this.code = code;
		this.descrption = descrption;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
}
