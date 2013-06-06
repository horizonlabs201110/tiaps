package cn.npnt.tiaps.assemble.vo;

public class RemindWriteBody {

	private Boolean result;//指定的消息项类型是否清零成功
	private String type;//发送清零请求的消息项类型 follower：新粉丝数
	
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
