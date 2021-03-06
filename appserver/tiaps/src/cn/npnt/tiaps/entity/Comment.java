package cn.npnt.tiaps.entity;

// Generated 2011-12-19 22:17:45 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Comment generated by hbm2java
 */
public class Comment implements java.io.Serializable {

	private Long id;
	private Integer version;
	private User user;
	private Look look;
	private Date createTime;
	private boolean deleted;
	private String comment;
	private Integer reportAbuse;
	private Integer agreeCount;//赞同数量
	private Set commentAgrees;

	public Comment() {
	}

	public Comment(Look look, boolean deleted) {
		this.look = look;
		this.deleted = deleted;
	}

	public Comment(User user, Look look, Date createTime, boolean deleted,
			String comment, Integer reportAbuse, Set commentAgrees) {
		this.user = user;
		this.look = look;
		this.createTime = createTime;
		this.deleted = deleted;
		this.comment = comment;
		this.reportAbuse = reportAbuse;
		this.commentAgrees = commentAgrees;
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
	public boolean isDeleted() {
		return this.deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getReportAbuse() {
		return this.reportAbuse;
	}
	public void setReportAbuse(Integer reportAbuse) {
		this.reportAbuse = reportAbuse;
	}
	public Set getCommentAgrees() {
		return this.commentAgrees;
	}
	public void setCommentAgrees(Set commentAgrees) {
		this.commentAgrees = commentAgrees;
	}
	public Integer getAgreeCount() {
		return agreeCount;
	}
	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}
}
