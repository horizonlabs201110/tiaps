package cn.npnt.tiaps.quartzTiming;

public class QuartzLookHotterConsBean {

	private Byte executeLookHotterDate;//执行计算多少天内的数据
	private Byte favoriteRatio;//收藏系数
	private Byte commentRatio;//评论系数
	private Byte repostRatio;//转发系数
	private Byte voteRatio;//投票系数
	
	public Byte getExecuteLookHotterDate() {
		return executeLookHotterDate;
	}
	public void setExecuteLookHotterDate(Byte executeLookHotterDate) {
		this.executeLookHotterDate = executeLookHotterDate;
	}
	public Byte getFavoriteRatio() {
		return favoriteRatio;
	}
	public void setFavoriteRatio(Byte favoriteRatio) {
		this.favoriteRatio = favoriteRatio;
	}
	public Byte getCommentRatio() {
		return commentRatio;
	}
	public void setCommentRatio(Byte commentRatio) {
		this.commentRatio = commentRatio;
	}
	public Byte getRepostRatio() {
		return repostRatio;
	}
	public void setRepostRatio(Byte repostRatio) {
		this.repostRatio = repostRatio;
	}
	public Byte getVoteRatio() {
		return voteRatio;
	}
	public void setVoteRatio(Byte voteRatio) {
		this.voteRatio = voteRatio;
	}
}
