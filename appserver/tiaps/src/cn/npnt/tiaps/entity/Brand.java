package cn.npnt.tiaps.entity;

// Generated 2011-12-19 22:17:45 by Hibernate Tools 3.4.0.CR1

/**
 * Brand generated by hbm2java
 */
public class Brand implements java.io.Serializable {

	private long id;
	private String title;

	public Brand() {
	}

	public Brand(String title) {
		this.title = title;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
