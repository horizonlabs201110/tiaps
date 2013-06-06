package cn.npnt.tiaps.service;

import java.util.List;
import java.util.Map;

import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.User;


public interface TimelineService {
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-2-5 下午9:21:36
	 * @description 给某一个用户在其timelien中添加一些look
	 */
	void addUserTimeline(List<Look> lookList,User addUser);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-4-5 下午4:09:18
	 * @description 根据param的条件，查询出满足条件的timeline个数
	 * 
	 */
	Integer getTimelineNum(Map<String,Object> param);
}
