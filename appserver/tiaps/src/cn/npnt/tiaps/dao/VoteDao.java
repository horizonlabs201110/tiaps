package cn.npnt.tiaps.dao;

import java.util.List;

import cn.npnt.tiaps.base.dao.GenericDao;
import cn.npnt.tiaps.entity.Vote;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2011 2011-12-27 上午12:32:37
 * @description 投票dao接口类
 */
public interface VoteDao extends GenericDao<Vote, Long> {

	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-27 上午12:38:10
	 * @description 根据lookId查询其各个图片评论总数 返回值map中，key 为图片在该组look中的下标，value表示投票的数量
	 */
	List<Integer> findLookVotedData(Long lookId);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-27 下午11:43:46
	 * @description 判断当前的用户是否已经对该look投票过
	 */
	Boolean isUserVotedLook(Long userId,Long lookId);
	
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author newnewmac
	 * @createDate 11:45:36 AM Jan 3, 2012
	 * @description 对Look进行投票
	 * @param userId
	 * @param photoIndex
	 * @return vote的ID
	 */
	 Long voteLook(Long userId, Long lookId, int photoIndex);
	
	
	 /**
	  * @company 新和新拓（北京）科技有限公司
	  * @author Recoba Gan
	  * @createDate 2012 2012-1-6 上午12:18:35
	  * @description 查询一个look被投了多少次票
	  */
	 Long findLookVotedCount(long lookId);
	 
	 /**
	  * @company 新和新拓（北京）科技有限公司
	  * @author Recoba Gan
	  * @createDate 2012 2012-1-14 上午12:55:47
	  * @description 查询一个look在一段时间内被投票过多少次
	  * 两个时间都可以为空，但lookId是不允许为空
	  */
	 Long findLookVotedByDate(String startDate,String endDate,Long lookId);
	
}
