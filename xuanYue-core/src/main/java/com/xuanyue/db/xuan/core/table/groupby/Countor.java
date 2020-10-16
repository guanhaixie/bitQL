package com.xuanyue.db.xuan.core.table.groupby;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import com.xuanyue.db.xuan.core.table.Source;

/**
 * 计数函数
 * @author guanh
 *
 */
public class Countor implements ICountor{
	
	private List<IAggrElement> splits;// = new ArrayList<>();
	private int maxId;
	public static int getSplit(int index) {
		return index/AggrElement.SIZE;
	}
	
	@Override
	public void forEche(int index,Number value) {
		inc(index);
	}
	public void inc(int index) {
		if(index>maxId) {
			return;
		}
		int split = getSplit(index);
		IAggrElement e = splits.get(split);
		ReadWriteLock rwl = e.getLock();
		Lock lock = rwl.writeLock();
		try {
			lock.lock();
			short[] vs = e.getData();
			vs[index%AggrElement.SIZE]++;
		} finally {
			lock.unlock();
		}
	}
	@Override
	public boolean check(int maxId) {
		return this.maxId == maxId;
	}
	@Override
	public void flush(int index) {
		int split = getSplit(index);
		//splits = MetaTable.managerShort.apply(split+1); 
		splits.forEach(e->{
			short[] d = e.getData();
			for(int i=0;i<d.length;i++) {
				d[i] = 0;
			}
		});
	}
}
