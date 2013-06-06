package cn.npnt.tiaps.service;

import java.util.List;

import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.entity.Favorite;
import cn.npnt.tiaps.entity.Look;

public interface FavoriteService {

	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-29 下午6:26:44
	 * @description 添加收藏
	 */
	Favorite addFavorite(long userId,long lookId)throws Exception;
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-29 下午6:40:03
	 * @description TODO
	 */
	long getLookFavoriteNum(Long lookId);
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-29 下午9:14:57
	 * @description 删除收藏
	 */
	boolean removeFavorite(Favorite favorite)throws Exception;
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-29 下午9:18:22
	 * @description 根据条件查询收藏对象
	 */
	List<Favorite> getFavoritesByCondition(Favorite param)throws Exception;
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2011 2011-12-30 下午11:12:07
	 * @description 给用户查询收藏列表
	 */
	List<Look> getFavoriteByUser(QueryParamVO param)throws Exception;
}
