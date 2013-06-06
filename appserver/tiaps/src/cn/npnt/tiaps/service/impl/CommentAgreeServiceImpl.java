package cn.npnt.tiaps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import cn.npnt.tiaps.dao.CommentAgreeDao;
import cn.npnt.tiaps.entity.CommentAgree;
import cn.npnt.tiaps.service.CommentAgreeService;


public class CommentAgreeServiceImpl implements CommentAgreeService {

	private CommentAgreeDao commentAgreeDao;
	@Autowired
	public void setCommentAgreeDao(CommentAgreeDao commentAgreeDao){
		this.commentAgreeDao = commentAgreeDao;
	}
	
	@Override
	public CommentAgree doAgreeWithComment(CommentAgree commentAgree)
			throws Exception {
		long id = commentAgreeDao.insert(commentAgree);
		commentAgree.setId(id);
		return commentAgree;
	}


}
