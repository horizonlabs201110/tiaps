package cn.npnt.tiaps.vo;

public class CommentVO {

	private Long id;
	private String text;
	private Integer agreeCount;
	private UserBrief userBrief;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getAgreeCount() {
		return agreeCount;
	}
	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}
	public UserBrief getUserBrief() {
		return userBrief;
	}
	public void setUserBrief(UserBrief userBrief) {
		this.userBrief = userBrief;
	}
}
