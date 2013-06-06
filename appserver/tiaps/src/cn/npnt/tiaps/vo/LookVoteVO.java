package cn.npnt.tiaps.vo;

public class LookVoteVO {

	private int index;
	private long count;
	
	/**
	 * @param index
	 * @param count
	 */
	public LookVoteVO(int index, long count) {
		this.index = index;
		this.count = count;
	}
	
	public LookVoteVO() {
	}



	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
