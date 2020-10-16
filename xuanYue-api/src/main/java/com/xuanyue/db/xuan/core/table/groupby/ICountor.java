package com.xuanyue.db.xuan.core.table.groupby;

import com.xuanyue.db.xuan.core.table.Source;

public interface ICountor extends Source,IAggr{
	
	public static int getSplit(int index) {
		return index/IAggrElement.SIZE;
	}
	public void inc(int index);
	
}
