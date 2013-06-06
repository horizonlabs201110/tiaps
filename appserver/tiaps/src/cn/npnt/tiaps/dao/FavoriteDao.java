package cn.npnt.tiaps.dao;

import java.util.List;

import cn.npnt.tiaps.base.dao.GenericDao;
import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.entity.Favorite;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2011 2011-12-27 下午11:46:29
 * @description 收藏功能Dao接口类
 */
public interface FavoriteDao extends GenericDao<Favorite, Long> {

	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-27 下午11:47:34
	 * @description 判断当前用户是否收藏了该look
	 */
	Boolean isUserAddFavorite(long userId,long lookId);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-29 下午8:48:38
	 * @description 查询某一个微博被收藏了多少次
	 */
	long findLookFavoriteNum(long lookId);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-14 上午12:42:08
	 * @description 查询某段时间内look被收藏的次数	两个时间都可以为空 look不允许为空
	 */
	long findLookFavoriteByDate(String startDate , String endDate , Long lookId);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-29 下午8:49:05
	 * @description 条件查询
	 */
	List<Favorite> findByCondition(Favorite param);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-30 下午11:13:18
	 * @description 给用户查询其下的收藏列表的look的id集合信息
	 */
	List<Long> findFavoritesByUser(QueryParamVO param);
}
