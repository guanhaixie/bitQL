package com.xuanyue.db.xuan.core.table.groupby;

import com.xuanyue.db.xuan.core.table.Source;

public interface ISumer_Min_Max extends Source,IAggr{

	
	public AggrOption getOp();
	public void setOp(AggrOption op);
	public int getMaxId();
	public void setMaxId(int maxId) ;
	public static int getSplit(int index) {
		return index/IAggrElement.SIZE;
	}
	public void max(int index,Number value);
	public void min(int index,Number value);
	public void sum(int index,Number value);
}
