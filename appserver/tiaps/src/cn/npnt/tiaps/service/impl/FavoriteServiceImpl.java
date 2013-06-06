package cn.npnt.tiaps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.dao.FavoriteDao;
import cn.npnt.tiaps.dao.LookDao;
import cn.npnt.tiaps.entity.Favorite;
import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.User;
import cn.npnt.tiaps.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

	private FavoriteDao favoriteDao;
	private LookDao lookDao;
	
	@Autowired
	public void setFavoriteDao(FavoriteDao favoriteDao){
		this.favoriteDao = favoriteDao;
	}
	@Autowired
	public void setLookDao(LookDao lookDao){
		this.lookDao = lookDao;
	}
	
	@Override
	public Favorite addFavorite(long userId, long lookId) throws Exception {
		//判断是否已经收藏了
		if(favoriteDao.isUserAddFavorite(userId, lookId) == false){
			Look look = new Look();
			look.setId(lookId);
			User user = new User();
			user.setId(userId);
			Favorite favorite = new Favorite();
			favorite.setCreateTime(new Date());
			favorite.setVersion(0);
			favorite.setLook(look);
			favorite.setUser(user);
			long id = favoriteDao.insert(favorite);
			favorite.setId(id);
			lookDao.manageLookFavorite(favorite.getLook(), 1);
			return favorite;
		}
		return null;
	}

	@Override
	public long getLookFavoriteNum(Long lookId) {
		return favoriteDao.findLookFavoriteNum(lookId);
	}

	@Override
	public boolean removeFavorite(Favorite favorite)throws Exception {
		favoriteDao.delete(favorite);
		lookDao.manageLookFavorite(favorite.getLook(), -1);
		return true;
	}

	@Override
	public List<Favorite> getFavoritesByCondition(Favorite param)
			throws Exception {
		List<Favorite> list = null;
		if(param != null){
			list = favoriteDao.findByCondition(param);
		}
		return list;
	}
	@Override
	public List<Look> getFavoriteByUser(QueryParamVO param)
			throws Exception {
		List<Look> list = null;
		List<Long> lookIdList = null;
		if(param != null && param.userId != null){
			lookIdList = favoriteDao.findFavoritesByUser(param);
		}
		if(lookIdList != null && lookIdList.size() > 0){
			list = lookDao.findLooksByIds(lookIdList);
		}
		return list;
	}

}
