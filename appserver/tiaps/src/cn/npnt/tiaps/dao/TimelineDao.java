package cn.npnt.tiaps.dao;

import java.util.List;
import java.util.Map;

import cn.npnt.tiaps.base.dao.GenericDao;
import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.entity.Timeline;
import cn.npnt.tiaps.entity.TimelineId;

public interface TimelineDao extends GenericDao<Timeline, TimelineId> {

	void insert(long userId,long lookId);
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-29 下午12:06:19
	 * @description 根据输入参数查询满足条件的所有look id集合 
	 */
	List<Long> getTimelineUserId(QueryParamVO param,Long sinceId,Long maxId)throws Exception;
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-10 下午11:53:44
	 * @description 按照分页查询，取出timeline中数量超过了maxNum的用户
	 */
	List<Long> findUnnecessaryTimelineUserId(QueryParamVO param,int maxNum);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-10 下午11:54:32
	 * @description 删除userId下面在timeline中数量超过了maxNum的timeline记录
	 */
	void removeUnnecessaryTimeline(long userId,int maxNum);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-11 下午2:47:03
	 * @description 删除某一个look对应的所有的timeline记录
	 */
	void removeLookTimeline(long lookId);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-2-15 下午10:39:05
	 * @description 删除followerId关注的followingId人的look的timeline数据
	 */
	Integer removeFollowingTimeline(long followerId,long followingId);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-4-5 下午3:50:05
	 * @description 按照条件查询出满足条件的timeline的记录个数
	 * 	如果 userId 不为空，则表示过滤出该用户的timeline
	 *  如果 minLookId 不为空，则表示需要过滤出lookId必须要大于该lookId
	 * 
	 */
	Integer getTimelineNum(Map<String,Object> param);
}
