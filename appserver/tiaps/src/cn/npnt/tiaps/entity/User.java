package cn.npnt.tiaps.entity;

// Generated 2011-12-16 0:03:47 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.Set;

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

	private Long id;
	private int version;
	private Date createTime;
	private boolean deleted;
	private String nickname;
	private String email;
	private String password;
	private String accessId;
	private int gender;
	private String mobile;
	private String introduce;
	private String userDesc;
	private String province;
	private String city;
	private String figureUrl;
	private short userType;
	private int snsType;
	private String snsId;
	private Boolean msgNotifiable;
	private Boolean emailNotifiable;
	private Date lastAcitveTime;
	private Date updateTime;
	private Set commentAgrees;
	private Set<Friendship> friendshipsForUserId;
	private Set<Friendship> friendshipsForFollowingId;
	private Set looks;
	private Set mentions;
	private Set shareToSnses;
	private Set<Vote> votes;
	private Set favorites;
	private Set comments;
	private Set timeLines;
	private Integer activability;
	
	//扩展出来的字段，相当vo来用
	private Boolean informed;

	public User() {
	}

	public User(Long id, Date createTime, boolean deleted, String nickname,
			String email, String password, int gender, short userType,
			int snsType) {
		this.id = id;
		this.createTime = createTime;
		this.deleted = deleted;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.userType = userType;
		this.snsType = snsType;
	}

	public User(long id, Date createTime, boolean deleted, String nickname,
			String email, String password, String accessId, int gender,
			String mobile, String introduce, String userDesc, String province,
			String city, String figureUrl, short userType, int snsType,
			String snsId, Boolean msgNotifiable, Boolean emailNotifiable,
			Date lastAcitveTime, Date updateTime, Set commentAgrees,
			Set<Friendship> friendshipsForUserId, Set<Friendship> friendshipsForFollowingId, Set looks,
			Set mentions, Set shareToSnses, Set<Vote> votes, Set favorites,
			Set comments, Set timeLines) {
		this.id = id;
		this.createTime = createTime;
		this.deleted = deleted;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.accessId = accessId;
		this.gender = gender;
		this.mobile = mobile;
		this.introduce = introduce;
		this.userDesc = userDesc;
		this.province = province;
		this.city = city;
		this.figureUrl = figureUrl;
		this.userType = userType;
		this.snsType = snsType;
		this.snsId = snsId;
		this.msgNotifiable = msgNotifiable;
		this.emailNotifiable = emailNotifiable;
		this.lastAcitveTime = lastAcitveTime;
		this.updateTime = updateTime;
		this.commentAgrees = commentAgrees;
		this.friendshipsForUserId = friendshipsForUserId;
		this.friendshipsForFollowingId = friendshipsForFollowingId;
		this.looks = looks;
		this.mentions = mentions;
		this.shareToSnses = shareToSnses;
		this.votes = votes;
		this.favorites = favorites;
		this.comments = comments;
		this.timeLines = timeLines;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccessId() {
		return this.accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getUserDesc() {
		return this.userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFigureUrl() {
		return this.figureUrl;
	}

	public void setFigureUrl(String figureUrl) {
		this.figureUrl = figureUrl;
	}

	public short getUserType() {
		return this.userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
	}

	public int getSnsType() {
		return this.snsType;
	}

	public void setSnsType(int snsType) {
		this.snsType = snsType;
	}

	public String getSnsId() {
		return this.snsId;
	}

	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}

	public Boolean getMsgNotifiable() {
		return this.msgNotifiable;
	}

	public void setMsgNotifiable(Boolean msgNotifiable) {
		this.msgNotifiable = msgNotifiable;
	}

	public Boolean getEmailNotifiable() {
		return this.emailNotifiable;
	}

	public void setEmailNotifiable(Boolean emailNotifiable) {
		this.emailNotifiable = emailNotifiable;
	}

	public Date getLastAcitveTime() {
		return this.lastAcitveTime;
	}

	public void setLastAcitveTime(Date lastAcitveTime) {
		this.lastAcitveTime = lastAcitveTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set getCommentAgrees() {
		return this.commentAgrees;
	}

	public void setCommentAgrees(Set commentAgrees) {
		this.commentAgrees = commentAgrees;
	}

	public Set<Friendship> getFriendshipsForUserId() {
		return this.friendshipsForUserId;
	}

	public void setFriendshipsForUserId(Set<Friendship> friendshipsForUserId) {
		this.friendshipsForUserId = friendshipsForUserId;
	}

	public Set<Friendship> getFriendshipsForFollowingId() {
		return this.friendshipsForFollowingId;
	}

	public void setFriendshipsForFollowingId(Set<Friendship> friendshipsForFollowingId) {
		this.friendshipsForFollowingId = friendshipsForFollowingId;
	}

	public Set getLooks() {
		return this.looks;
	}

	public void setLooks(Set looks) {
		this.looks = looks;
	}

	public Set getMentions() {
		return this.mentions;
	}

	public void setMentions(Set mentions) {
		this.mentions = mentions;
	}

	public Set getShareToSnses() {
		return this.shareToSnses;
	}

	public void setShareToSnses(Set shareToSnses) {
		this.shareToSnses = shareToSnses;
	}

	public Set<Vote> getVotes() {
		return this.votes;
	}

	public void setVotes(Set<Vote> votes) {
		this.votes = votes;
	}

	public Set getFavorites() {
		return this.favorites;
	}

	public void setFavorites(Set favorites) {
		this.favorites = favorites;
	}

	public Set getComments() {
		return this.comments;
	}

	public void setComments(Set comments) {
		this.comments = comments;
	}
	public Set getTimeLines() {
		return this.timeLines;
	}
	public void setTimeLines(Set timeLines) {
		this.timeLines = timeLines;
	}
	public Integer getActivability() {
		return activability;
	}
	public void setActivability(Integer activability) {
		this.activability = activability;
	}
	public Boolean getInformed() {
		return informed;
	}
	public void setInformed(Boolean informed) {
		this.informed = informed;
	}
}
