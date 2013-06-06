package cn.npnt.tiaps.service;

import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.ShareToSns;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2012 2012-1-3 下午3:30:38
 * @description 分享到第三方sns
 */
public interface ShareToSnsService {

	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-3 下午4:30:10
	 * @description 分享到第三放sns记录service
	 */
	ShareToSns reportToThirdParty(Look originalLook , ShareToSns shareToSns) throws Exception;
}
