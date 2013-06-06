package cn.npnt.tiaps.entity;

// Generated 2011-12-19 22:17:45 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Friendship generated by hbm2java
 */
public class Friendship implements java.io.Serializable {

	private Long id;
	private int version;
	private User userByUserId;
	private User userByFollowingId;
	private Date createTime;
	private boolean informed;

	public Friendship() {
	}

	public Friendship(User userByUserId, User userByFollowingId,
			Date createTime, boolean informed) {
		this.userByUserId = userByUserId;
		this.userByFollowingId = userByFollowingId;
		this.createTime = createTime;
		this.informed = informed;
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

	public User getUserByUserId() {
		return this.userByUserId;
	}

	public void setUserByUserId(User userByUserId) {
		this.userByUserId = userByUserId;
	}

	public User getUserByFollowingId() {
		return this.userByFollowingId;
	}

	public void setUserByFollowingId(User userByFollowingId) {
		this.userByFollowingId = userByFollowingId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isInformed() {
		return this.informed;
	}

	public void setInformed(boolean informed) {
		this.informed = informed;
	}

}