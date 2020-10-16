package com.xuanyue.db.xuan.core.table;

public interface ISortElement {
	public IBitIndex getData();
	public boolean isDesc() ;
	public void setData(IBitIndex data);
	public void setDesc(boolean desc);
}
