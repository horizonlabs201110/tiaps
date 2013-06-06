package cn.npnt.tiaps.vo;

public class WearBrandVO {

	private String brand;
	private String clothing;
	
	
	public WearBrandVO() {
		super();
	}
	public WearBrandVO(String brand, String clothing) {
		super();
		this.brand = brand;
		this.clothing = clothing;
	}
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getClothing() {
		return clothing;
	}
	public void setClothing(String clothing) {
		this.clothing = clothing;
	}
	
	
}
