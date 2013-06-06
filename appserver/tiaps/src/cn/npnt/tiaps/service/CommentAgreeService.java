package cn.npnt.tiaps.service;

import cn.npnt.tiaps.entity.CommentAgree;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2012 2012-1-5 下午12:07:25
 * @description 对评论进行投票的业务接口
 */
public interface CommentAgreeService {

	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-5 下午12:08:24
	 * @description 对评论进行投票
	 */
	CommentAgree doAgreeWithComment(CommentAgree commentAgree)throws Exception;
	
}
