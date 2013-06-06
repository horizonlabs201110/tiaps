package cn.npnt.tiaps.vo;

public class UserBrief {

	private Long id;
	private String nickname;
	private Byte gender;
	private String introduce;
	private String province;
	private String city;
	private String figureUrl;
	private Boolean isMyFollowing;
	private Boolean isMyFollower;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Byte getGender() {
		return gender;
	}
	public void setGender(Byte gender) {
		this.gender = gender;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getFigureUrl() {
		return figureUrl;
	}
	public void setFigureUrl(String figureUrl) {
		this.figureUrl = figureUrl;
	}
	public Boolean getIsMyFollowing() {
		return isMyFollowing;
	}
	public void setIsMyFollowing(Boolean isMyFollowing) {
		this.isMyFollowing = isMyFollowing;
	}
	public Boolean getIsMyFollower() {
		return isMyFollower;
	}
	public void setIsMyFollower(Boolean isMyFollower) {
		this.isMyFollower = isMyFollower;
	}
	
	
	
}
