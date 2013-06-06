package cn.npnt.tiaps.workSchedule;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.npnt.tiaps.base.util.Worker;
import cn.npnt.tiaps.dao.LookDao;
import cn.npnt.tiaps.dao.TimelineDao;
import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.User;

public class DeleteLookWorker extends Worker {

	private Logger logger = Logger.getLogger(DeleteLookWorker.class);
	private Look look;
	private User user;
	
	private TimelineDao timelineDao;
	private LookDao lookDao;
	
	
	@Override
	public String doWorker() throws Exception {
		logger.info("删除look时候启动的异步线程执行****************************");
		//删除timeline里面关于该条微博的数据。
		timelineDao.removeLookTimeline(look.getId());
		//删除其下所有的子look（将子look的parentDeleted属性赋值为true）
		doDelSonLook(look.getId());
		
		return null;
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-11 下午11:05:07
	 * @description 暂时不考虑一次性load数据量过多导致内存溢出的情况，后期需要改进
	 */
	private void doDelSonLook(Long lookId){
		List<Look> list = lookDao.findSonOfLooks(lookId);
		if(list != null && list.size() > 0){
			for(Look look : list){
				logger.info("delete look --> lookId : " + look.getId());
				look.setParentDeleted(true);
				//timelineDao.removeLookTimeline(look.getId()); 是否有必要删除？？？
				lookDao.update(look);
				doDelSonLook(look.getId());
			}
		}
	}


	
	public Look getLook() {
		return look;
	}
	public void setLook(Look look) {
		this.look = look;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Autowired
	public void setTimelineDao(TimelineDao timelineDao) {
		this.timelineDao = timelineDao;
	}
	@Autowired
	public void setLookDao(LookDao lookDao) {
		this.lookDao = lookDao;
	}

	
	
}
