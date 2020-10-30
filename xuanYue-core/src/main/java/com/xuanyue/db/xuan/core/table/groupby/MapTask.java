package com.xuanyue.db.xuan.core.table.groupby;

import com.xuanyue.db.xuan.core.index.IntIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.IXGHBitSet;

public class MapTask<T extends Number> extends Thread{

	private int split;
	private IXGHBitSet where;
	private IntIndex key;
	private IColumn data;
	private Countor count;
	private Sumer_Min_Max smm;
	private Avg avg;
	private int op;
	
	@Override
	public void run() {
		
		int offset = split*IXGHBitSet.SET_SIZE;
		Number t;
		for (int i = where.nextSetBit(0); i >= 0; i = where.nextSetBit(i+1)) {
			t = (Number)data.get(i+offset);
			switch(op) {
				case 1://sum
				case 2://min
				case 3://max 
					smm.forEche(key.get(i+offset), t.floatValue());
					break;
				case 4://count
					count.forEche(key.get(i+offset), 1);
					break;
				case 5://avg
					avg.forEche(key.get(i+offset), t.floatValue());
					break;
			}
		}
	}
}
