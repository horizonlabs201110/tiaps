package cn.npnt.tiaps.entity;

import java.util.Date;

// Generated 2011-12-28 15:10:15 by Hibernate Tools 3.4.0.CR1

/**
 * Timeline generated by hbm2java
 */
public class Timeline implements java.io.Serializable {

	private User user;
	private Look look;
	private Date createTime;
	private TimelineId id;
	
	public Timeline() {
	}

	public Timeline(User user, Look look) {
		this.user = user;
		this.look = look;
	}
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Look getLook() {
		return this.look;
	}
	public void setLook(Look look) {
		this.look = look;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public TimelineId getId() {
		return id;
	}

	public void setId(TimelineId id) {
		this.id = id;
	}
	
}
