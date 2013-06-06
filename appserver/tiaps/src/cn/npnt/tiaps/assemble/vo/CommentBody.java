package cn.npnt.tiaps.assemble.vo;

import java.util.List;

import cn.npnt.tiaps.vo.CommentVO;

public class CommentBody {

	private List<CommentVO> comments;
	private Long commentId;
	private Integer agreeCount;
	

	public List<CommentVO> getComments() {
		return comments;
	}
	public void setComments(List<CommentVO> comments) {
		this.comments = comments;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public Integer getAgreeCount() {
		return agreeCount;
	}
	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}
	
	
}
