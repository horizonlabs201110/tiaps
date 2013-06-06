package cn.npnt.tiaps.entity;

// Generated 2011-12-19 22:17:45 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Look generated by hbm2java
 */
public class Look implements java.io.Serializable {

	private Long id;
	private int version;
	private User user;
	private Look lookByParentId;
	private Look lookByOriginalId;
	private boolean deleted;
	private boolean parentDeleted;
	private Date createTime;
	private Date displayTime;
	private Long occasionId;//场景id
	private String text;
	private String searchText;
	private Byte repostType;
	private Set<Photo> photos;//为了针对多图一个look的时候的顺序结构
	private Set mentions;
	private Set<LookTrend> lookTrends;
	private LookStatistic lookStatistic;
	private Set<Comment> comments;
	private Set looksForOriginalId;
	private Set timelines;
	private Set looksForParentId;
	private Set votes;
	private Set favorites;
	private Set timeLines;
	private Set shareToSnses;

	public Look() {
		
	}

	public Look(User user, boolean deleted, Date createTime) {
		this.user = user;
		this.deleted = deleted;
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Look getLookByParentId() {
		return lookByParentId;
	}

	public void setLookByParentId(Look lookByParentId) {
		this.lookByParentId = lookByParentId;
	}

	public Look getLookByOriginalId() {
		return lookByOriginalId;
	}

	public void setLookByOriginalId(Look lookByOriginalId) {
		this.lookByOriginalId = lookByOriginalId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(Date displayTime) {
		this.displayTime = displayTime;
	}

	public Long getOccasionId() {
		return occasionId;
	}

	public void setOccasionId(Long occasionId) {
		this.occasionId = occasionId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Byte getRepostType() {
		return repostType;
	}

	public void setRepostType(Byte repostType) {
		this.repostType = repostType;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	public Set getMentions() {
		return mentions;
	}

	public void setMentions(Set mentions) {
		this.mentions = mentions;
	}

	public Set<LookTrend> getLookTrends() {
		return lookTrends;
	}

	public void setLookTrends(Set<LookTrend> lookTrends) {
		this.lookTrends = lookTrends;
	}

	public LookStatistic getLookStatistic() {
		return lookStatistic;
	}

	public void setLookStatistic(LookStatistic lookStatistic) {
		this.lookStatistic = lookStatistic;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set getLooksForOriginalId() {
		return looksForOriginalId;
	}

	public void setLooksForOriginalId(Set looksForOriginalId) {
		this.looksForOriginalId = looksForOriginalId;
	}

	public Set getTimelines() {
		return timelines;
	}

	public void setTimelines(Set timelines) {
		this.timelines = timelines;
	}

	public Set getLooksForParentId() {
		return looksForParentId;
	}

	public void setLooksForParentId(Set looksForParentId) {
		this.looksForParentId = looksForParentId;
	}

	public Set getVotes() {
		return votes;
	}

	public void setVotes(Set votes) {
		this.votes = votes;
	}

	public Set getFavorites() {
		return favorites;
	}

	public void setFavorites(Set favorites) {
		this.favorites = favorites;
	}

	public Set getTimeLines() {
		return timeLines;
	}

	public void setTimeLines(Set timeLines) {
		this.timeLines = timeLines;
	}

	public Set getShareToSnses() {
		return shareToSnses;
	}

	public void setShareToSnses(Set shareToSnses) {
		this.shareToSnses = shareToSnses;
	}
	public boolean isParentDeleted() {
		return parentDeleted;
	}
	public void setParentDeleted(boolean parentDeleted) {
		this.parentDeleted = parentDeleted;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	List<Photo> getPhotoList(){
		List<Photo> list = null;
		if(photos != null && photos.size() > 0){
			list = new ArrayList<Photo>();
			Photo p = null;
			for(byte i=0;i<photos.size();i++){
				p = getIndexPhoto(i);
				if(p != null){
					list.add(p);
				}
			}
		}
		return list;
	}
	
	public Photo getIndexPhoto(byte index){
		if(this.photos != null && this.photos.size() > 0){
			for(Photo p : photos){
				if(p.getIndex() == index){
					return p;
				}
			}
		}
		return null;
	}
}