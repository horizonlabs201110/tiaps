package cn.npnt.tiaps.quartzTiming;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.dao.TimelineDao;

public class QuartzTimingManageTimeLine extends QuartzJobBean {

	private Logger logger = Logger.getLogger(QuartzTimingManageTimeLine.class);
	private TimelineDao timelineDao;
	static final int MAX_NUM = 500;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("============================================= execute timeline job");
		XmlBeanFactory bf = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
		this.timelineDao = (TimelineDao)bf.getBean("timelineDao");
		QueryParamVO param = new QueryParamVO();
		List<Long> idList = null;
		do{
			idList = timelineDao.findUnnecessaryTimelineUserId(param, MAX_NUM);
			if(idList != null && idList.size() > 0){
				for(Long id : idList){
					logger.info("QuartzTimingManageTimeLine execute userId : " + id 
							+ " 's more than max number " + MAX_NUM + " of timeline  ");
					timelineDao.removeUnnecessaryTimeline(id, MAX_NUM);
				}
			}
		}while(idList != null && idList.size() == MAX_NUM);
	}
}
//delete from qrtz_simple_triggers;
//delete from Qrtz_triggers;
//delete from qrtz_job_details;
