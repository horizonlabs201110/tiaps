package cn.npnt.tiaps.entity;

// Generated 2011-12-28 15:10:15 by Hibernate Tools 3.4.0.CR1

/**
 * CommentAgree 对评论进行的投票
 */
public class CommentAgree implements java.io.Serializable {

	private long id;
	private long version;
	private User user;
	private Comment comment;

	public CommentAgree() {
	}

	public CommentAgree(User user, Comment comment) {
		this.user = user;
		this.comment = comment;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comment getComment() {
		return this.comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
