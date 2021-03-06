package cn.npnt.tiaps.entity;

// Generated 2011-12-19 22:17:45 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Photo generated by hbm2java
 */
public class Photo implements java.io.Serializable {

	private Long id;
	private int version;
	private Look look;
	private boolean deleted;
	private Date createTime;
	private String picUrl;
	private Integer vistedCount;
	private Byte index;
	private String thumbUrl;
	private String shopUrl;
	private Set<WearBrand> wearBrands;//采用list主要是为了有序的
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
	public Look getLook() {
		return look;
	}
	public void setLook(Look look) {
		this.look = look;
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
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getVistedCount() {
		return vistedCount;
	}
	public void setVistedCount(Integer vistedCount) {
		this.vistedCount = vistedCount;
	}
	public Byte getIndex() {
		return index;
	}
	public void setIndex(Byte index) {
		this.index = index;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public String getShopUrl() {
		return shopUrl;
	}
	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}
	public Set<WearBrand> getWearBrands() {
		return wearBrands;
	}
	public void setWearBrands(Set<WearBrand> wearBrands) {
		this.wearBrands = wearBrands;
	}
}
