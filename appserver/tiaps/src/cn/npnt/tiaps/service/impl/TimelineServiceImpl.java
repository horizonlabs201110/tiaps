package cn.npnt.tiaps.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.npnt.tiaps.dao.TimelineDao;
import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.Timeline;
import cn.npnt.tiaps.entity.User;
import cn.npnt.tiaps.service.TimelineService;

public class TimelineServiceImpl implements TimelineService {

	private TimelineDao timelineDao;
	
	@Autowired
	public void setTimelineDao(TimelineDao timelineDao){
		this.timelineDao = timelineDao;
	}
	
	@Override
	public void addUserTimeline(List<Look> lookList, User addUser) {
		if(lookList != null && lookList.size() > 0 && addUser != null){
			Timeline timeline = null;
			for(Look look : lookList){
				timelineDao.insert(addUser.getId(),look.getId());
			}
		}
	}

	@Override
	public Integer getTimelineNum(Map<String, Object> param) {
		Integer number = 0;
		if(param != null && param.size() > 0){
			number = timelineDao.getTimelineNum(param);
		}
		return number;
	}

}
