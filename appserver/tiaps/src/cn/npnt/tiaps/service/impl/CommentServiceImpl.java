package cn.npnt.tiaps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.dao.CommentDao;
import cn.npnt.tiaps.entity.Comment;
import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.service.CommentService;

public class CommentServiceImpl implements CommentService {

	private CommentDao commentDao;
	
	@Autowired
	public void setCommentDao(CommentDao commentDao){
		this.commentDao = commentDao;
	}
	
	@Override
	public void doLookComment(Comment comment) throws Exception {
		commentDao.insert(comment);
	}

	@Override
	public List<Comment> getCommentsByLook(QueryParamVO param, Long lookId)
			throws Exception {
		if(param == null){
			param = new QueryParamVO();
		}
		Look look = new Look();
		look.setId(lookId);
		Comment comment = new Comment();
		comment.setLook(look);
		return commentDao.findCommentsByCondition(param, comment);
	}

	@Override
	public Comment getByPK(long id) {
		return commentDao.findByPK(Comment.class, id);
	}

	@Override
	public Comment manageCommentAgreeCount(Comment comment, int times)
			throws Exception {
		if(comment != null && times != 0){
			if(comment.getAgreeCount() + times <= 0){
				comment.setAgreeCount(0);
			}else{
				comment.setAgreeCount(comment.getAgreeCount() + times);
			}
			commentDao.update(comment);
		}
		return comment;
	}

	@Override
	public List<Comment> getCommentWithMaxId(QueryParamVO param, Long maxId,
			Comment vo) {
		return commentDao.findCommentWithMaxId(param, maxId, vo);
	}

}
