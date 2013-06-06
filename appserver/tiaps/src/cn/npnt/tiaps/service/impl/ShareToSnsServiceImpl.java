package cn.npnt.tiaps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import cn.npnt.tiaps.dao.LookDao;
import cn.npnt.tiaps.dao.ShareToSnsDao;
import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.ShareToSns;
import cn.npnt.tiaps.service.ShareToSnsService;

public class ShareToSnsServiceImpl implements ShareToSnsService {

	private ShareToSnsDao shareToSnsDao;
	private LookDao lookDao;
	
	@Autowired
	public void setShareToSnsDao(ShareToSnsDao shareToSnsDao){
		this.shareToSnsDao = shareToSnsDao;
	}
	public void setLookDao(LookDao lookDao){
		this.lookDao = lookDao;
	}
	
	@Override
	public ShareToSns reportToThirdParty(Look originalLook , ShareToSns shareToSns)
			throws Exception {
		Long id = shareToSnsDao.insert(shareToSns);
		shareToSns.setId(id);
		originalLook.getLookStatistic().setRepostCount(originalLook.getLookStatistic().getRepostCount() + 1);
		lookDao.update(originalLook);
		return shareToSns;
	}

}
