package cn.npnt.tiaps.workSchedule;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.npnt.tiaps.base.util.Worker;
import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.dao.FriendshipDao;
import cn.npnt.tiaps.dao.MentionDao;
import cn.npnt.tiaps.dao.TimelineDao;
import cn.npnt.tiaps.dao.UserDao;
import cn.npnt.tiaps.entity.Friendship;
import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.Mention;
import cn.npnt.tiaps.entity.Timeline;
import cn.npnt.tiaps.entity.User;

public class LookSendTimeline extends Worker {

	private Logger logger = Logger.getLogger(LookSendTimeline.class);
	private Look look;
	private User user;
	
	private TimelineDao timelineDao;
	private FriendshipDao friendshipDao;
	private UserDao userDao;
	private MentionDao mentionDao;
	
	@Autowired
	public void setTimelineDao(TimelineDao timelineDao){
		this.timelineDao = timelineDao;
	}
	@Autowired
	public void setFriendshipDao(FriendshipDao friendshipDao){
		this.friendshipDao = friendshipDao;
	}
	@Autowired
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	@Autowired
	public void setMentionDao(MentionDao mentionDao){
		this.mentionDao = mentionDao;
	}
	
	public LookSendTimeline(){
		
	}
	public LookSendTimeline(User user,Look look){
		this.user = user;
		this.look = look;
	}
	
	@Override
	public String doWorker() throws Exception {
		if(look == null || user == null 
				|| StringUtils.isBlank(look.getText())
				|| look.getText().length() > 200){
			logger.info("维护timeline数据参数异常");
		}else{
			List<String> addUserList = getAddUserName(look.getText());
			Timeline timeline = null;
			//发送给@给某些人
			if(addUserList != null && addUserList.size() > 0){
				List<User> userList = null;
				User userParam = new User();
				User addUser = null;
				Mention mention = null;//add给某人的时候需要给提醒表里加上一条记录
				for(String name : addUserList){
					userParam.setNickname(name);
					userList = userDao.findByCondition(userParam);//根据用户nickname查询出来用户对象
					if(userList != null && userList.size() == 1){
						addUser = userList.get(0);
						//判断该addUer是否是已经是look发表者的粉丝，如果是发表者的粉丝，则timeline由粉丝列表维护
						//如果是@给自己的话，则由下面自己去维护
						if(friendshipDao.isFollower(user.getId(), addUser.getId()) == false
								&& user.getId() != addUser.getId()){
							timelineDao.insert(userList.get(0).getId(),look.getId());
							logger.info("timeline add --> userId :" + userList.get(0).getId()
									+ " lookId : " + look.getId());
						}
						//给提醒表加数据时不分双方是否是互为粉丝
						mention = new Mention();
						mention.setLook(look);
						mention.setUser(addUser);
						mention.setVersion(1);
						mention.setInformed(false);
						mentionDao.insert(mention);
					}
				}
			}
			//获取粉丝列表，把timeline推送给其粉丝
			QueryParamVO param = new QueryParamVO();
			param.pageSize = 200;//每批次200
			param.userId = user.getId();
			List<Friendship> friendshipList = friendshipDao.getAllFollower(user, param);
			do{
				if(friendshipList != null && friendshipList.size() > 0){
					for(Friendship ship : friendshipList){
						timelineDao.insert(ship.getUserByUserId().getId(),look.getId());
						logger.info("timeline add --> userId :" + ship.getUserByUserId().getId()
								+ " lookId : " + look.getId());
					}
				}
				param.pageNO ++;
				friendshipList = friendshipDao.getAllFollower(user, param);
			}while(friendshipList != null && friendshipList.size() == 200);
			//给自己发送一份timeline
			timeline = new Timeline(user,look);
			timelineDao.insert(user.getId(),look.getId());
			logger.info("timeline add --> userId :" + user.getId()
					+ " lookId : " + look.getId());
		}
		return null;
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
	
	private List<String> getAddUserName(String content){
		List<String> userNameList = null;
		String name = null;
		if(content.contains("@")){
			userNameList = new ArrayList<String>();
			for(int i=0;i<content.length();i++){
				if(content.charAt(i) == '@'){
				sec:for(int j=i;j<content.length();j++){
						if(content.charAt(j) == ' '){
							name = content.substring(i+1,j);
							if(userNameList.contains(name) == false){
								userNameList.add(content.substring(i+1,j));
							}
							i = j;
							break sec;
						}
					}
				}
			}
		}
		return userNameList;
	}
}
