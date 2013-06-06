package cn.npnt.tiaps.web;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.npnt.tiaps.quartzTiming.QuartzJobExecuteTimeBean;
import cn.npnt.tiaps.quartzTiming.QuartzTimingManageLookHotter;
import cn.npnt.tiaps.quartzTiming.QuartzTimingManageTimeLine;
import cn.npnt.tiaps.quartzTiming.QuartzTimingManageUserHotter;

public class InitSystemWorkerListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(InitSystemWorkerListener.class);
	private Scheduler scheduler;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.err.print("init system jobs success <<<<<<<<<<<<<<<<<<<<<");
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		QuartzJobExecuteTimeBean executeTimeBean = (QuartzJobExecuteTimeBean)wac.getBean("jobExecuteTimeBean");
		String timeline = "2:00";
		String userHotter = "3:00";
		String lookHotter = "4:00";
		if(executeTimeBean != null){
			if(StringUtils.isNotBlank(executeTimeBean.getTimelineTime())){
				timeline = executeTimeBean.getTimelineTime();
			}
			if(StringUtils.isNotBlank(executeTimeBean.getUserHotterTime())){
				userHotter = executeTimeBean.getUserHotterTime();
			}
			if(StringUtils.isNotBlank(executeTimeBean.getLookHotterTime())){
				lookHotter = executeTimeBean.getLookHotterTime();
			}
		}
		this.scheduler = (Scheduler) wac.getBean("scheduler");
		createTimelineManageJob(scheduler,getTriggerDate(timeline));
		createUserHotterJob(scheduler,getTriggerDate(userHotter));
		createLookHotterJob(scheduler,getTriggerDate(lookHotter));
	}
	
	private static Date getTriggerDate(String time)throws NumberFormatException{
		Calendar calendar = Calendar.getInstance();
		Date current = calendar.getTime();
		String[] timeArr = null;
		if(time.contains(":")){
			 timeArr = time.split(":");
		}else if(time.contains("：")){
			timeArr = time.split("：");
		}else{
			timeArr = new String[2];
			timeArr[0] = "2";
			timeArr[1] = "0";
		}
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]));
		calendar.set(Calendar.SECOND, 0);
		logger.info(calendar.getTime());
		if(current.getTime() > calendar.getTimeInMillis()){
			calendar.add(Calendar.DATE, 1);
		}
		return calendar.getTime();
	}
	private void createTimelineManageJob(Scheduler scheduler,Date executeDate){
		logger.info("创建timeline的维护job");
		try {
			scheduler.deleteJob("manage-Timeline", "system-auto");
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
		// 创建处理timeline中冗余的数据定时任务
		JobDetail jobDetail = new JobDetailBean();
		jobDetail.setJobClass(QuartzTimingManageTimeLine.class);
		jobDetail.setGroup("system-auto");
		jobDetail.setName("manage-Timeline");
		SimpleTrigger simpleTrigger = new SimpleTrigger("manage-Timeline-trigger","system-auto");
		simpleTrigger.setStartTime(executeDate);
		simpleTrigger.setRepeatCount(9999999);
		simpleTrigger.setRepeatInterval(24*60*60*1000);//1000表示1秒，即单位为毫秒数
		try {
			scheduler.scheduleJob(jobDetail, simpleTrigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	private void createUserHotterJob(Scheduler scheduler,Date executeDate){
		try {
			scheduler.deleteJob("manage-userHotter", "system-auto");
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
		//创建维护用户热度的定时任务
		JobDetail userHotterJob = new JobDetailBean();
		userHotterJob.setJobClass(QuartzTimingManageUserHotter.class);
		userHotterJob.setGroup("system-auto");
		userHotterJob.setName("manage-userHotter");
		SimpleTrigger userTrigger = new SimpleTrigger("manage-userHotter-trigger","system-auto");
		Calendar calendar = Calendar.getInstance();
		userTrigger.setStartTime(executeDate);
		logger.info("维护用户job执行时间 .. " + (executeDate.getTime() - calendar.getTime().getTime())/1000);
		userTrigger.setRepeatCount(9999999);
		userTrigger.setRepeatInterval(24*60*60*1000);
		try {
			scheduler.scheduleJob(userHotterJob, userTrigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	//创建look热度定时任务
	private void createLookHotterJob(Scheduler scheduler,Date executeDate){
		try {
			scheduler.deleteJob("manage-lookHotter", "system-auto");
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
		JobDetail lookHotterJob = new JobDetailBean();
		lookHotterJob.setJobClass(QuartzTimingManageLookHotter.class);
		lookHotterJob.setGroup("system-auto");
		lookHotterJob.setName("manage-lookHotter");
		SimpleTrigger trigger = new SimpleTrigger("manage-lookHotter-trigger","system-auto");
		trigger.setStartTime(executeDate);
		trigger.setRepeatCount(9999999);
		trigger.setRepeatInterval(24*60*60*1000);
		try {
			scheduler.scheduleJob(lookHotterJob, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	} 
}