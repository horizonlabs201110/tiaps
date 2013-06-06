package cn.npnt.tiaps.entity;

// Generated 2011-12-19 22:17:45 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * ShareToSns generated by hbm2java
 */
public class ShareToSns implements java.io.Serializable {

	private Long id;
	private int version;
	private User user;
	private Look look;
	private Date createTime;
	private Byte snsType;
	private String text;

	public ShareToSns() {
	}

	public ShareToSns(User user, Look look, Byte snsType) {
		this.user = user;
		this.look = look;
		this.snsType = snsType;
	}

	public ShareToSns(User user, Look look, Date createTime, Byte snsType,
			String text) {
		this.user = user;
		this.look = look;
		this.createTime = createTime;
		this.snsType = snsType;
		this.text = text;
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
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Byte getSnsType() {
		return this.snsType;
	}

	public void setSnsType(Byte snsType) {
		this.snsType = snsType;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}