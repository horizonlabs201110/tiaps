package cn.npnt.tiaps.quartzTiming;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.dao.FriendshipDao;
import cn.npnt.tiaps.dao.LookDao;
import cn.npnt.tiaps.dao.UserDao;
import cn.npnt.tiaps.entity.User;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2012 2012-1-11 下午11:38:02
 * @description 维护用户热度定时执行任务
 */
public class QuartzTimingManageUserHotter extends QuartzJobBean {

	private Logger logger = Logger.getLogger(QuartzTimingManageUserHotter.class);
	private static final int BATCH_NUM = 50;
	private UserDao userDao;
	private FriendshipDao friendshipDao;
	private LookDao lookDao;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		logger.info("----------------------维护用户热度job开始执行");
		DateFormat formator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		XmlBeanFactory bf = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
		QuartzUserHotterConsBean dataBean = (QuartzUserHotterConsBean)bf.getBean("userHotterBean");
		userDao = (UserDao) bf.getBean("userDao");
		friendshipDao = (FriendshipDao)bf.getBean("friendshipDao");
		lookDao = (LookDao)bf.getBean("lookDao");
		Calendar calendar = Calendar.getInstance();
		Date current = calendar.getTime();
		Date sinceDate = null;//从什么时候开始计算，到现在的时间（确定统计时间范围）
		Byte followerRatio = null;
		Byte originalLookRatio = null;
		Byte repostLookRatio = null;
		if(dataBean.getExecuteUserHotterDate() != null && dataBean.getExecuteUserHotterDate() > 0){
			Integer executeDateNum = Math.abs(dataBean.getExecuteUserHotterDate());
			calendar.add(Calendar.DATE, (executeDateNum - executeDateNum *2));
			sinceDate = calendar.getTime();
		}else{
			calendar.add(Calendar.DATE, 2);
			sinceDate = calendar.getTime();
		}
		if(dataBean.getFollowerRatio() != null && dataBean.getFollowerRatio() > 0){
			followerRatio = dataBean.getFollowerRatio();
		}
		if(dataBean.getOriginalLookRatio() != null && dataBean.getOriginalLookRatio() > 0){
			originalLookRatio = dataBean.getOriginalLookRatio();
		}
		if(dataBean.getRepostLookRatio() != null && dataBean.getRepostLookRatio() > 0){
			repostLookRatio = dataBean.getRepostLookRatio();
		}
		//计算用户热度主要数据分布：
			//1.在一段时间内时间内用户被关注的次数.
			//2.在一段时间内用户发布的look数量.
			//3.在一段时间内用户发布的look被转发的次数.
		String startDate = formator.format(sinceDate);
		String endDate = formator.format(current);
		int hotter = 0;
		QueryParamVO userQuery = new QueryParamVO(null,BATCH_NUM,1,null,null);
		List<User> userPitch = null;
		long numberOfUser = 0;
		do{
			userPitch = userDao.findUserByPage(userQuery, null);
			userQuery.pageNO++;
			if(userPitch != null && userPitch.size() > 0){
				for(User user : userPitch){
					if(followerRatio != null){
						hotter = friendshipDao.findUserFollowersNumByDate(startDate, endDate, user.getId()) * followerRatio;
					}
					if(originalLookRatio != null){
						hotter += lookDao.findUserPostLookNum(startDate, endDate, null, user.getId()) * originalLookRatio;
					}
					if(repostLookRatio != null){
						hotter += lookDao.findBeenRepostNum(startDate, endDate, null, user.getId());
					}
					user.setActivability(hotter);
					userDao.update(user);
					logger.info("update user : " + user.getId() + " hotter value : " + hotter);
					hotter = 0;
				}
				numberOfUser = numberOfUser + userPitch.size();
			}
		}while(userPitch != null && userPitch.size() == BATCH_NUM);
		logger.info("QuartzTimingManageUserHotter==========更新了总共: " + numberOfUser + "名用户==============");
	}

}
