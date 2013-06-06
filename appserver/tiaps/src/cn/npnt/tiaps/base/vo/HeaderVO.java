package cn.npnt.tiaps.base.vo;

import cn.npnt.tiaps.base.constants.ApplicationConstant;
import cn.npnt.tiaps.constants.Constants;

public class HeaderVO {
	
	private int ret;//响应状态标示
	private String errorMsg;//错误信息
	
	/**
	 * 无参构造（正确响应结果）
	 */
	public HeaderVO(){
		ret = ApplicationConstant.RET_SUCCESS;
	}
	
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
