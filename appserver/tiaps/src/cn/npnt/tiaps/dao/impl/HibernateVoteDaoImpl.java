package cn.npnt.tiaps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;

import cn.npnt.tiaps.base.dao.impl.HibernateGenericDao;
import cn.npnt.tiaps.constants.Constants;
import cn.npnt.tiaps.dao.VoteDao;
import cn.npnt.tiaps.entity.Friendship;
import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.User;
import cn.npnt.tiaps.entity.Vote;

public class HibernateVoteDaoImpl extends HibernateGenericDao<Vote, Long>
		implements VoteDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findLookVotedData(Long lookId) {
		final List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i< Constants.MAX_PHOTO_NUM + 1;i++){
			list.add(0);
		}
		final String hql = "select photo_index pindex,count(*) count from vote where look_id= " 
					+ lookId + " group by photo_index order by photo_index";
		jdbcTemplate.execute(new StatementCallback() {
			@Override
			public Object doInStatement(Statement statement) throws SQLException,
					DataAccessException {
				ResultSet rs = statement.executeQuery(hql.toString());
				Long count = (long) 0;
				while(rs.next()){
					count = rs.getLong("count");
					if(count > 0){
						list.set(rs.getInt("pindex"), count.intValue());
					}
				}
				rs.close();
				return null;
			}
		});
		return list;
	}

	@Override
	public Boolean isUserVotedLook(Long userId, Long lookId) {
		// TODO Auto-generated method stub
		Boolean retValue =new Boolean(false);
		List<Friendship> list = null;
		StringBuilder cond = new StringBuilder("from Vote where 1=1 ");
		cond.append(" and user.id = " + userId);
		cond.append(" and look.id = " + lookId);
		list = getHibernateTemplate().find(cond.toString());
		if(list.size() > 0){
			retValue = Boolean.TRUE;
		}
		return retValue;
	}

	/* (non-Javadoc)
	 * @see cn.npnt.tiaps.dao.VoteDao#voteLook(java.lang.Long, java.lang.Long, int)
	 */
	@Override
	public Long voteLook(Long userId, Long lookId, int photoIndex) {
		// TODO Auto-generated method stub
		Long id = null;
		Vote vote = new Vote();
		User userEntity = new User();
		userEntity.setId(userId);
		Look lookEntity = new Look();
		lookEntity.setId(lookId);
		vote.setUser(userEntity);
		vote.setLook(lookEntity);
		vote.setPhotoIndex((byte)photoIndex);
		vote.setCreateTime(new Date());
		vote.setVersion(0);
		return this.insert(vote);
	}
	@Override
	public Long findLookVotedCount(long lookId) {
		String sql = "select count(*) from vote where look_id=" + lookId;
		return jdbcTemplate.queryForLong(sql);
	}
	@Override
	public Long findLookVotedByDate(String startDate, String endDate,
			Long lookId) {
		StringBuilder sql = new StringBuilder("select count(*) from vote where look_id=");
		sql.append(lookId);
		if(StringUtils.isNotBlank(startDate)){
			sql.append(" and create_time >='").append(startDate).append("' ");
		}
		if(StringUtils.isNotBlank(endDate)){
			sql.append(" and create_time <='").append(endDate).append("' ");
		}
		return jdbcTemplate.queryForLong(sql.toString());
	}


	
	
	
}
