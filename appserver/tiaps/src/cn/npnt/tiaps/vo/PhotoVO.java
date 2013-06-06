package cn.npnt.tiaps.vo;

import java.util.Set;

public class PhotoVO {

//	{
//		  "index" : 1,
//		  "picUrl" : "http://npnt.cn/asd.jpg",
//		  "thumbUrl" : "http://npnt.cn/asd.jpg",
//		  "shopUrl" : "www.nike.com",
//		  "wareBrands" : [
//		    {
//		      "brand" : "niki",
//		      "clothing" : "鞋子"
//		    },
//		    {
//		      "brand" : "niki",
//		      "clothing" : "鞋子"
//		    }
//		  ]
//		}
	private Byte index;
	private String picUrl;
	private String thumbUrl;
	private String shopUrl;
	private Set<WearBrandVO> wareBrands;
	
	public Byte getIndex() {
		return index;
	}
	public void setIndex(Byte index) {
		this.index = index;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
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
	public Set<WearBrandVO> getWareBrands() {
		return wareBrands;
	}
	public void setWareBrands(Set<WearBrandVO> wareBrands) {
		this.wareBrands = wareBrands;
	}
}
