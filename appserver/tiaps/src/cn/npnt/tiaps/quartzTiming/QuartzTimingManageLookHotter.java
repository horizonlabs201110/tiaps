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
import cn.npnt.tiaps.dao.CommentDao;
import cn.npnt.tiaps.dao.FavoriteDao;
import cn.npnt.tiaps.dao.LookDao;
import cn.npnt.tiaps.dao.VoteDao;
import cn.npnt.tiaps.entity.Look;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2012 2012-1-14 上午12:01:31
 * @description look热度跑批类
 */
public class QuartzTimingManageLookHotter extends QuartzJobBean {

	private Logger logger = Logger.getLogger(QuartzTimingManageLookHotter.class);
	private static final int BATCH_NUM = 20; 
	private LookDao lookDao;
	private FavoriteDao favoriteDao;
	private CommentDao commentDao;
	private VoteDao voteDao;
	
	@Override
	protected void executeInternal(JobExecutionContext content)
			throws JobExecutionException {
		DateFormat formator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		XmlBeanFactory bf = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
		QuartzLookHotterConsBean lookHotterCons = (QuartzLookHotterConsBean)bf.getBean("lookHotterBean");//look跑批常量
		lookDao = (LookDao)bf.getBean("lookDao");
		favoriteDao = (FavoriteDao)bf.getBean("favoriteDao");
		commentDao = (CommentDao)bf.getBean("commentDao");
		voteDao = (VoteDao)bf.getBean("voteDao");
		Calendar calendar = Calendar.getInstance();
		Date current = calendar.getTime();
		
		Date sinceDate = null;//从什么时候开始计算，到现在的时间（确定统计时间范围）
		Byte favoriteRatio = null;
		Byte repostRatio = null;
		Byte commentRatio = null;
		Byte voteRatio = null;
		if(lookHotterCons.getExecuteLookHotterDate() != null && lookHotterCons.getExecuteLookHotterDate() > 0){
			Integer executeDateNum = Math.abs(lookHotterCons.getExecuteLookHotterDate());
			calendar.add(Calendar.DATE, (executeDateNum - executeDateNum *2));
			sinceDate = calendar.getTime();
		}else{
			calendar.add(Calendar.DATE, 2);//default two days
			sinceDate = calendar.getTime();
		}
		String startDate = formator.format(sinceDate);
		String endDate = formator.format(current);
		if(lookHotterCons.getFavoriteRatio() != null && lookHotterCons.getFavoriteRatio() > 0){
			favoriteRatio = lookHotterCons.getFavoriteRatio();
		}
		if(lookHotterCons.getRepostRatio() != null && lookHotterCons.getRepostRatio() > 0){
			repostRatio = lookHotterCons.getRepostRatio();
		}
		if(lookHotterCons.getCommentRatio() != null && lookHotterCons.getCommentRatio() > 0){
			commentRatio = lookHotterCons.getCommentRatio();
		}
		if(lookHotterCons.getVoteRatio() != null && lookHotterCons.getVoteRatio() > 0){
			voteRatio = lookHotterCons.getVoteRatio();
		}
		
		long numberOfUser= 0;
		boolean isHasMore = false;
		QueryParamVO param = new QueryParamVO(null,BATCH_NUM,1,null,null);
		do{
			List<Look> list = lookDao.findLookByPage(param);
			param.pageNO++;
			if(list != null && list.size() > 0){
				for(Look look : list){
					int hotter = 0;
					if(favoriteRatio != null && favoriteRatio > 0){
						hotter += favoriteRatio * favoriteDao.findLookFavoriteByDate(
								startDate, endDate, look.getId());
					}
					if(repostRatio != null && repostRatio > 0){
						hotter += repostRatio * lookDao.findBeanRepostLook(startDate, endDate, look.getId());
					}
					if(commentRatio != null && commentRatio > 0){
						hotter += commentRatio * commentDao.findLookCommentByDate(startDate, endDate, look.getId());
					}
					if(voteRatio != null && voteRatio > 0){
						hotter += voteRatio * voteDao.findLookVotedByDate(startDate, endDate, look.getId());
					}
					look.getLookStatistic().setHotter(hotter);
					lookDao.update(look);
					logger.info("update look id : " + look.getId() + " hotter value : " + hotter);
					hotter = 0;
				}
				if(list.size() == BATCH_NUM){
					isHasMore = true;
				}else{
					isHasMore = false;
				}
				numberOfUser = numberOfUser + list.size();
			}else{
				isHasMore = false;
			}
		}while(isHasMore);
		logger.info("QuartzTimingManageLookHotter==========更新了总共: " + numberOfUser + " 个look==============");
	}

}
