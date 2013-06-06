package cn.npnt.tiaps.assemble.vo;

public class RemindReadBody {
	private int status;	//未读取的消息数量
	private int follower;//未读取的粉丝数量
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getFollower() {
		return follower;
	}
	public void setFollower(int follower) {
		this.follower = follower;
	}
}
