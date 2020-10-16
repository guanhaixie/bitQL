package com.xuanyue.db.xuan.core.table.sort;

import com.xuanyue.db.xuan.core.index.BitIndex;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.ISortElement;

public class SortElement implements ISortElement{

	private IBitIndex data;
	private boolean desc;
	public IBitIndex getData() {
		return data;
	}
	public boolean isDesc() {
		return desc;
	}
	public void setData(IBitIndex data) {
		this.data = data;
	}
	public void setDesc(boolean desc) {
		this.desc = desc;
	}
	
	
	
}
