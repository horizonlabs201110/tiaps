package cn.npnt.tiaps.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;

import cn.npnt.tiaps.base.dao.impl.HibernateGenericDao;
import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.dao.FavoriteDao;
import cn.npnt.tiaps.entity.Favorite;

public class HibernateFavoriteDaoImpl extends
		HibernateGenericDao<Favorite, Long> implements FavoriteDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Boolean isUserAddFavorite(long userId, long lookId) {
		String sql = "select count(*) from favorite where user_id=" + userId 
				+ " and look_id=" + lookId;
		long num = jdbcTemplate.queryForLong(sql);
		if(num > 0){
			return true;
		}
		return false;
	}

	@Override
	public long findLookFavoriteNum(long lookId) {
		String sql = "select count(*) from favorite where look_id= " + lookId;
		return jdbcTemplate.queryForLong(sql);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Favorite> findByCondition(Favorite param){
		List<Favorite> list = null;
		if(param != null){
			StringBuilder hql = new StringBuilder(" from Favorite where 1=1 ");
			if(param.getId() != null){
				hql.append(" where id=").append(param.getId());
			}
			if(param.getUser() != null && param.getUser().getId() != null){
				hql.append(" and user.id=").append(param.getUser().getId());
			}
			if(param.getLook() != null && param.getLook().getId() != null){
				hql.append(" and look.id=").append(param.getLook().getId());
			}
			list = getHibernateTemplate().find(hql.toString());
		}
		return list;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findFavoritesByUser(QueryParamVO param) {
		List<Long> list = null;
		if(param != null && param.userId != null){
			final StringBuilder hql = new StringBuilder("select look_id from favorite where user_id= ");
			hql.append(param.userId);
			if(param.sinceId != null && param.sinceId > 0){
				hql.append(" and id<").append(param.sinceId);
			}
			if(StringUtils.isNotBlank(param.orderBy)){
				hql.append(" order by ").append("create_time ");
			}
			if(StringUtils.isNotBlank(param.desc)){
				hql.append(" ").append(param.desc);
			}
			if(param.pageNO != null && param.pageSize != null){
				hql.append(" limit ").append((param.pageNO - 1)*param.pageSize).
					append(",").append(param.pageNO*param.pageSize);
			}
			list = jdbcTemplate.execute(new StatementCallback() {
				@Override
				public Object doInStatement(Statement statement)
						throws SQLException, DataAccessException {
					ResultSet rs = statement.executeQuery(hql.toString());
					List<Long> list = new ArrayList<Long>();
					while(rs.next()){
						list.add(rs.getLong(1));
					}
					return list;
				}
			});
		}
		return list;
	}

	@Override
	public long findLookFavoriteByDate(String startDate, String endDate,
			Long lookId) {
		String sql = "select count(*) from favorite where look_id= " + lookId;
		if(StringUtils.isNotBlank(startDate)){
			sql += " and create_time >='" + startDate + "' ";
		}
		if(StringUtils.isNotBlank(endDate)){
			sql += " and create_time <='" + endDate +"'";
		}
		return jdbcTemplate.queryForLong(sql);
	}

}
