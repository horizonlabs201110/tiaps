package cn.npnt.tiaps.quartzTiming;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2012 2012-1-11 下午11:41:26
 * @description 所有job需要在spring配置的常量数值
 */
public class QuartzUserHotterConsBean {

	private Byte executeUserHotterDate;//计算多少日之内的热度

	private Byte followerRatio;//有多少人加关注的系数
	
	private Byte originalLookRatio;//原创look的系数
	
	private Byte repostLookRatio;//look被转发系数
	
	
	public Byte getExecuteUserHotterDate() {
		return executeUserHotterDate;
	}
	public void setExecuteUserHotterDate(Byte executeUserHotterDate) {
		this.executeUserHotterDate = executeUserHotterDate;
	}
	public Byte getFollowerRatio() {
		return followerRatio;
	}
	public void setFollowerRatio(Byte followerRatio) {
		this.followerRatio = followerRatio;
	}
	public Byte getOriginalLookRatio() {
		return originalLookRatio;
	}
	public void setOriginalLookRatio(Byte originalLookRatio) {
		this.originalLookRatio = originalLookRatio;
	}
	public Byte getRepostLookRatio() {
		return repostLookRatio;
	}
	public void setRepostLookRatio(Byte repostLookRatio) {
		this.repostLookRatio = repostLookRatio;
	}
}
