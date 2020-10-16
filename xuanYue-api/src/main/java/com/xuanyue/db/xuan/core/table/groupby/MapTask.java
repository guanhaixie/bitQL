package com.xuanyue.db.xuan.core.table.groupby;

import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.IXGHBitSet;

public class MapTask<T extends Number> extends Thread{

	
	private int split;
	private IXGHBitSet where;
	private IColumn<Integer> key;
	private IColumn<T> data;
	private ICountor count;
	private ISumer_Min_Max smm;
	private IAvg avg;
	private AggrOption op;
	
	@Override
	public void run() {
		
		int offset = split*IXGHBitSet.SET_SIZE;
		T t;
		for (int i = where.nextSetBit(0); i >= 0; i = where.nextSetBit(i+1)) {
			t = data.get(i+offset);
			switch(op) {
				case SUM://sum
				case MIN://min
				case MAX://max 
					smm.forEche(key.get(i+offset), t.floatValue());
					break;
				case COUNT://count
					count.forEche(key.get(i+offset), 1);
					break;
				case AVG://avg
					avg.forEche(key.get(i+offset), t.floatValue());
					break;
			}
			
			
		}
		
		
		
		
		
		
	}
	
	
	
	
	
}
