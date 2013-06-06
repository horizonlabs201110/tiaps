package cn.npnt.tiaps.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 *  通用的数据访问接口，包含常用的
 *  insert,update,delete,selectByPk方法。
 * 该接口是所有的数据访问接口的父接口。
 * @since jdk1.5
 */
public interface GenericDao<T, PK extends Serializable> {
	
	/**
	  * 插入一个实体对象。
	  * 实体的类型在子接口中说明。
	  * @param entity 实体对象。
	  * @throws DataAccessException执行数据访问操作发生错误时。
	  */
	Long insert(T entity) throws DataAccessException;
	/** 
	 * 插入或更新多个实体
	 * @param entities
	 * @throws DataAccessException
	 */
	void insertOrUpdateAll(Collection<T> entities) throws DataAccessException;

	/**	 
		  * 更新一个实体对象。
		  * 实体的类型在子接口中说明。
		  * @param entity 实体对象。
		  * @throws DataAccessException执行数据访问操作发生错误时。
	 */
	void update(T entity) throws DataAccessException;

	/**	 
	  *删除一个实体对象。
	  * 实体的类型在子接口中说明。
	  * @param entity 实体对象。
	  * @throws DataAccessException执行数据访问操作发生错误时。
*/
	void delete(T entity) throws DataAccessException;

	/**	 
	  *删除一个实体对象。
	  * 实体的类型在子接口中说明。
	  * @param entity 实体对象。
	  * @throws DataAccessException执行数据访问操作发生错误时。
*/
	void delete(Class<T> type, PK primaryKey) throws DataAccessException;

	/**	 
	  *根据标示符查询一个实体对象。
	  * 实体的类型在子接口中说明。
	  * @param entity 实体对象。
	  * @throws DataAccessException执行数据访问操作发生错误时。
*/
	T findByPK(Class<T> type, PK primaryKey) throws DataAccessException;
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-18 下午4:19:34
	 * @description 查询所有的记录
	 */
	List<T> findAll(Class<T> type , String orderBy);
	
	/**
	 *获取数据库的时间
	 *
	 *@return Date类型的时间
	 *@param 没有参数
	 *@throw DateAccessException()执行数据库访问发生错误
	 */	
	Date getDatabaseCurrentTime() throws DataAccessException;
}