package cn.npnt.tiaps.base.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.npnt.tiaps.base.dao.GenericDao;


/**
 *	通用的数据访问接口基于Hibernate的实现。
 * @author 李佳
 * @since jdk1.5
 * @since hibernate3
 */
public abstract class HibernateGenericDao<T, PK extends Serializable> extends HibernateDaoSupport
		implements GenericDao<T, PK> {
	
	
	
	public void insertOrUpdateAll(Collection<T> entities) throws DataAccessException {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public Long insert(T entity) {
		return (Long) getHibernateTemplate().save(entity);
	}

	public void update(T entity) {
		 getHibernateTemplate().update(entity);
	}

	public void delete(T entity)  {
		 getHibernateTemplate().delete(entity);
	}

	public void delete(Class<T> type, PK primaryKey) {
		T entity =  getHibernateTemplate().get(type, primaryKey);
		if(entity != null){
			 getHibernateTemplate().delete(entity);
		}
	}

	public T findByPK(Class<T> type, PK primaryKey){
		return  getHibernateTemplate().get(type, primaryKey);
	}

	public List<T> findAll(Class<T> type , String orderBy){
		return getHibernateTemplate().find(" from " + type.getSimpleName() + " order by " + orderBy);
	}
	
	 public Date getDatabaseCurrentTime() {
			String hql = "select current_timestamp()";
			return  (Date)  getHibernateTemplate().find(hql).get(0);
	}
}