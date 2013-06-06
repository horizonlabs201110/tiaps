package cn.npnt.tiaps.dao;

import java.util.List;

import cn.npnt.tiaps.base.dao.GenericDao;
import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.entity.Comment;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2012 2012-1-2 下午10:03:01
 * @description 评论Dao接口
 */
public interface CommentDao extends GenericDao<Comment, Long> {

	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-27 下午11:52:32
	 * @description 根据look 的id查找该look有多少个comment
	 */
	long findLookCommentCount(long lookId);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-14 上午12:49:01
	 * @description 查询一个look在一段时间内被评论了多少次
	 * 	两个时间都是可以为空，但lookId是不允许为空
	 */
	long findLookCommentByDate(String startDate,String endDate,Long lookId);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-4 下午11:45:12
	 * @description 条件查询对look的评论
	 */
	List<Comment> findCommentsByCondition(QueryParamVO param , Comment comment);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-7 上午10:25:40
	 * @description 带有maxId的条件查询
	 */
	List<Comment> findCommentWithMaxId(QueryParamVO param,Long maxId,Comment vo);
}
