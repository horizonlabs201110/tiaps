package cn.npnt.tiaps.service;

import java.util.List;

import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.entity.Comment;

public interface CommentService {

	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-4 下午11:06:43
	 * @description 对look进行评价
	 */
	void doLookComment(Comment comment)throws Exception;
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-4 下午11:43:39
	 * @description 查询look旗下的评论对象
	 */
	List<Comment> getCommentsByLook(QueryParamVO param , Long lookId)throws Exception;
	
	Comment getByPK(long id);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-5 下午12:27:29
	 * @description 对评论进行投票处理，times是整数则表示加几次，负数表示减几次，最多将投票度减到0
	 */
	Comment manageCommentAgreeCount(Comment comment,int times)throws Exception;
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-7 上午10:25:40
	 * @description 带有maxId的条件查询
	 * param.pageNo or param.pageSize 小于等于0的时候，表示不分页
	 * maxId 如果为空则表示不分页，不为空则表示需要分页
	 * 
	 */
	List<Comment> getCommentWithMaxId(QueryParamVO param,Long maxId,Comment vo);
}
