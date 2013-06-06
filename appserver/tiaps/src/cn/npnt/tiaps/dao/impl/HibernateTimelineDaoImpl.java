package cn.npnt.tiaps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;

import cn.npnt.tiaps.base.dao.impl.HibernateGenericDao;
import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.dao.TimelineDao;
import cn.npnt.tiaps.entity.Timeline;
import cn.npnt.tiaps.entity.TimelineId;

public class HibernateTimelineDaoImpl extends
		HibernateGenericDao<Timeline, TimelineId> implements TimelineDao {

	private Logger logger = Logger.getLogger(HibernateTimelineDaoImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public List<Long> getTimelineUserId(QueryParamVO param, Long sinceId,
			Long maxId) throws Exception {
		final StringBuilder hql = new StringBuilder("select look_id from timeline where 1=1 ");
		if(param.userId != null){
			hql.append(" and user_id=").append(param.userId);
		}
		if(sinceId != null){
			hql.append(" and look_id <").append(sinceId).
				append(" order by look_id ").append(param.desc).
				append(" limit ").append(param.pageSize);
		}else if(maxId != null){
			hql.append(" and look_id >").append(maxId).
			append(" order by look_id ").append(param.desc).
			append(" limit ").append(param.pageSize);
		}else{
			hql.append(" order by look_id ").append(param.desc);
			hql.append(" limit ").append((param.pageNO - 1)*param.pageSize).append(",").
					append(param.pageNO*param.pageSize);
		}
		final List<Long> lookIdList = new ArrayList<Long>();
		jdbcTemplate.execute(new StatementCallback<Object>() {
			@Override
			public Object doInStatement(Statement stament) throws SQLException,
					DataAccessException {
				logger.info(hql.toString());
				ResultSet rs = stament.executeQuery(hql.toString());
				while(rs.next()){
					lookIdList.add(rs.getLong(1));
				}
				rs.close();
				return lookIdList;
			}
			
		});
		return lookIdList;
	}


	@Override
	public List<Long> findUnnecessaryTimelineUserId(QueryParamVO param,int maxNum) {
		final StringBuilder sql = new StringBuilder("select user_id from");
		sql.append("(select user_id , count(*) c from timeline ")
		.append("group by user_id order by user_id desc) temp where c >")
		.append(maxNum).append(" limit ").append((param.pageNO - 1)*param.pageSize)
		.append(",").append(param.pageNO*param.pageSize);
		return jdbcTemplate.execute(new StatementCallback() {
			@Override
			public Object doInStatement(Statement statement) throws SQLException,
					DataAccessException {
				List<Long> list = new ArrayList<Long>();
				ResultSet rs = statement.executeQuery(sql.toString());
				while(rs.next()){
					list.add(rs.getLong("user_id"));
				}
				return list;
			}
			
		});
	}
	@Override
	public void removeUnnecessaryTimeline(long userId , int maxNum) {
		long timelineNum = this.findUserTimelineNum(userId);
		logger.info("removeUnnecessaryTimeline  userId : " + userId + " 's timeline number is " + timelineNum);
		if(timelineNum > maxNum){
			logger.info(" execute remove more than " + maxNum +" 's timeline data ");
			String sql = "delete from timeline where look_id in(select look_id from (select @rownum:=@rownum+1 rownum,look_id from (select * from timeline where user_id=" 
					+userId +" order by look_id desc) user_timeline,(SELECT @rownum:=0) r) temp where rownum > " + maxNum
					+")";
			logger.info("removeUnnecessaryTimeline sql : " + sql);
			jdbcTemplate.execute(sql);
		}
	}
	
	@Override
	public void removeLookTimeline(long lookId) {
		String sql = "delete from timeline where look_id=" + lookId;
		jdbcTemplate.execute(sql);
	}


	@Override
	public Integer removeFollowingTimeline(long followerId, long followingId) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select look_id from timeline tl,look lk where lk.user_id=")
			.append(followingId).append(" and tl.look_id=lk.id and tl.user_id=")
			.append(followerId).append(" and tl.look_id not in(select t.look_id from timeline t, mention m where t.user_id=")
			.append(followerId).append(" and t.user_id=m.user_id and t.look_id=m.look_id)");
		final List<Long> ids = new ArrayList<Long>();
		jdbcTemplate.execute(new StatementCallback() {
			@Override
			public Object doInStatement(Statement statement) throws SQLException,
					DataAccessException {
				ResultSet rs = statement.executeQuery(sql.toString());
				while(rs.next()){
					ids.add(rs.getLong("look_id"));
				}
				return null;
			}
			
		});
		if(ids.size() > 0){
			StringBuilder delSql = new StringBuilder("delete from timeline where user_id=");
			delSql.append(followerId).append(" and look_id in(");
			for(Long l : ids){
				delSql.append(l).append(",");
			}
			delSql.deleteCharAt(delSql.length() -1).append(")");
			jdbcTemplate.execute(delSql.toString());
		}
		return null;
	}

	@Override
	public void insert(long userId, long lookId) {
		StringBuilder sql = new StringBuilder("insert into timeline(user_id,look_id) values(");
		sql.append(userId).append(",").append(lookId).append(")");
		try {
			jdbcTemplate.execute(sql.toString());
			logger.info("timeline里插入了userId :" + userId + " lookId :" + lookId);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 查询某一个用户的timeline个数
	 */
	private Long findUserTimelineNum(long userId){
		String sql = "select count(*) from timeline where user_id=" + userId;
		return jdbcTemplate.queryForLong(sql);
	}


	@Override
	public Integer getTimelineNum(Map<String, Object> param) {
		StringBuilder sql = new StringBuilder("select count(*) from timeline where 1=1 ");
		if(param != null && param.size() > 0){
			if(param.get("userId") != null){
				sql.append(" and user_id=").append(param.get("userId")).append(" ");
			}
			if(param.get("minLookId") != null){
				sql.append(" and look_id >").append(param.get("minLookId")).append(" ");
			}
		}
		Long number = jdbcTemplate.queryForLong(sql.toString());
		return number.intValue();
	}
}
