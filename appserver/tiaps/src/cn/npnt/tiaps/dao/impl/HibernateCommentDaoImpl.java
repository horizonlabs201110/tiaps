package cn.npnt.tiaps.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.npnt.tiaps.base.dao.impl.HibernateGenericDao;
import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.dao.CommentDao;
import cn.npnt.tiaps.entity.Comment;

public class HibernateCommentDaoImpl extends HibernateGenericDao<Comment, Long>
		implements CommentDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public long findLookCommentCount(long lookId) {
		String sql = "select count(*) from comment where look_id=" + lookId;
		return jdbcTemplate.queryForLong(sql);
	}

	@Override
	public long findLookCommentByDate(String startDate, String endDate,
			Long lookId) {
		StringBuilder sql = new StringBuilder("select count(*) from comment where look_id=");
		sql.append(lookId);
		if(StringUtils.isNotBlank(startDate)){
			sql.append(" and create_time >='").append(startDate).append("' ");
		}
		if(StringUtils.isNotBlank(endDate)){
			sql.append(" and create_time<='").append(endDate).append("'");
		}
		return jdbcTemplate.queryForLong(sql.toString());
	}
	
	@Override
	public List<Comment> findCommentsByCondition(QueryParamVO param,
			Comment comment) {
		StringBuilder hql = new StringBuilder("from Comment where 1=1");
		if(comment != null){
			if(comment.getLook() != null && comment.getLook().getId() != null){
				hql.append(" and look.id=").append(comment.getLook().getId());
			}
			if(comment.getUser() != null && comment.getUser().getId() != null){
				hql.append(" and user.id=").append(comment.getUser().getId());
			}
		}
		if(param != null){
			hql.append(" order by ").append(param.orderBy).append(" ").append(param.desc);
		}
		return getHibernateTemplate().find(hql.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findCommentWithMaxId(final QueryParamVO param, Long maxId,
			Comment vo) {
		final StringBuilder sql = new StringBuilder("from Comment where 1=1 ");
		if(vo != null){
			if(vo.getLook() != null && vo.getLook().getId() != null){
				sql.append(" and look.id=").append(vo.getLook().getId());
			}
			if(vo.getUser() != null && vo.getUser().getId() != null){
				sql.append(" and user.id=").append(vo.getUser().getId());
			}
		}
		if(param != null){
			if(maxId != null){//分页
				sql.append(" and id >").append(maxId);
			}
			//maxId为空值的时候查询所有
			sql.append(" order by ").append(param.orderBy).append(" ").append(param.desc);
		}
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(sql.toString());
				if(param != null){
					if(param.pageNO > 0 && param.pageSize > 0){
						query = query.setFirstResult((param.pageNO - 1)*param.pageSize);
						query = query.setMaxResults(param.pageSize);
					}
				}
				return query.list();
			}
		});
	}

}
