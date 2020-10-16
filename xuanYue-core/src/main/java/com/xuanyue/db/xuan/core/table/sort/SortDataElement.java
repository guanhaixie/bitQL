package com.xuanyue.db.xuan.core.table.sort;

public class SortDataElement implements Comparable<SortDataElement>{

	private int index;
	private String sort;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public SortDataElement(int index, String sort) {
		super();
		this.index = index;
		this.sort = sort;
	}
	@Override
	public int compareTo(SortDataElement o) {
		return sort.compareTo(o.sort);
	}
	
	
}
