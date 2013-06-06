package cn.npnt.tiaps.vo;

import java.util.List;

public class LookVO {
	private Long id;
	private UserBrief userBrief;
	private UserBrief reporterBrief;
	private List<PhotoVO> photos;
	private List<LookVoteVO> votes;
	
	private String verdict;
	private String text;
	private String occasion;
	private Boolean hasVoted;
	private Boolean hasFavorited;
	private Long commentCount;
	private Long repostCount;
	private Long favoriteCount;
	private String dateTime;//"YYYY-MM-dd HH:mm:ss"
	
	private String reportText;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserBrief getUserBrief() {
		return userBrief;
	}
	public void setUserBrief(UserBrief userBrief) {
		this.userBrief = userBrief;
	}
	public String getVerdict() {
		return verdict;
	}
	public void setVerdict(String verdict) {
		this.verdict = verdict;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getOccasion() {
		return occasion;
	}
	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}
	public Boolean getHasVoted() {
		return hasVoted;
	}
	public void setHasVoted(Boolean hasVoted) {
		this.hasVoted = hasVoted;
	}
	public Boolean getHasFavorited() {
		return hasFavorited;
	}
	public void setHasFavorited(Boolean hasFavorited) {
		this.hasFavorited = hasFavorited;
	}
	public Long getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	public Long getRepostCount() {
		return repostCount;
	}
	public void setRepostCount(Long repostCount) {
		this.repostCount = repostCount;
	}
	public Long getFavoriteCount() {
		return favoriteCount;
	}
	public void setFavoriteCount(Long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public List<PhotoVO> getPhotos() {
		return photos;
	}
	public void setPhotos(List<PhotoVO> photos) {
		this.photos = photos;
	}
	public List<LookVoteVO> getVotes() {
		return votes;
	}
	public void setVotes(List<LookVoteVO> votes) {
		this.votes = votes;
	}
	public UserBrief getReporterBrief() {
		return reporterBrief;
	}
	public void setReporterBrief(UserBrief reporterBrief) {
		this.reporterBrief = reporterBrief;
	}
	public String getReportText() {
		return reportText;
	}
	public void setReportText(String reportText) {
		this.reportText = reportText;
	}
}
