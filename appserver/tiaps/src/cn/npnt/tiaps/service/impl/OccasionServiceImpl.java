package cn.npnt.tiaps.service.impl;import java.util.ArrayList;import java.util.List;import cn.npnt.tiaps.dao.OccasionDao;import cn.npnt.tiaps.entity.Occasion;import cn.npnt.tiaps.service.OccasionService;import cn.npnt.tiaps.vo.OccasionVO;public class OccasionServiceImpl implements OccasionService {	private OccasionDao occasionDao;		public OccasionDao getOccasionDao() {		return occasionDao;	}	public void setOccasionDao(OccasionDao occasionDao) {		this.occasionDao = occasionDao;	}	/*	 * (non-Javadoc)	 * @see cn.npnt.tiaps.service.OccasionService#getByPK(long)	 */	@Override	public Occasion getByPK(long id) {		return occasionDao.findByPK(Occasion.class, id);	}	@Override	public List<Occasion> getAll() {		return occasionDao.findAll(Occasion.class, "index");	}	@Override	public List<OccasionVO> getAllVO() {		List<OccasionVO> voList = null;		List<Occasion> list = occasionDao.findAll(Occasion.class, "index");		if(list != null){			voList = new ArrayList<OccasionVO>();			OccasionVO vo = null;			for(Occasion o : list){				vo = new OccasionVO(o.getId(),o.getTitle());				voList.add(vo);			}		}		return voList;	}}