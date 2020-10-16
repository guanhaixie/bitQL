package com.xuanyue.db.xuan.core.table.groupby;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import com.xuanyue.db.xuan.core.table.MetaTable;

public class Sumer_Min_Max implements ISumer_Min_Max{

	private List<IAggrElement> splits;// = new ArrayList<>();
	private AggrOption op;
	private int maxId;
	
	public AggrOption getOp() {
		return op;
	}
	public void setOp(AggrOption op) {
		this.op = op;
	}
	public int getMaxId() {
		return maxId;
	}
	public void setMaxId(int maxId) {
		this.maxId = maxId;
	}
	public static int getSplit(int index) {
		return index/AggrElement.SIZE;
	}
	@Override
	public boolean check(int maxId) {
		return this.maxId == maxId;
	}
	@Override
	public void flush(int index) {
		int split = getSplit(index);
		splits = MetaTable.managerFloat.apply(split+1);
		this.maxId = index;
		if(op.eq(AggrOption.SUM)) {
			//sum
			splits.forEach(e->{
				float[] v = e.getData();
				for(int i=0;i<AggrElement.SIZE;i++) {
					v[i]=0;
				}
			});			
		}else if(op.eq(AggrOption.MIN)) {
			//min
			splits.forEach(e->{
				float[] v = e.getData();
				for(int i=0;i<AggrElement.SIZE;i++) {
					v[i]=Integer.MAX_VALUE;
				}
			});
		}else {
			//max
			splits.forEach(e->{
				float[] v = e.getData();
				for(int i=0;i<AggrElement.SIZE;i++) {
					v[i]=Integer.MIN_VALUE;
				}
			});
		}
	}
	
	@Override
	public void forEche(int index,Number value) {
		if(op.eq(AggrOption.SUM)) {
			//sum
			sum(index, value);
		}else if(op.eq(AggrOption.MIN)) {
			//min
			min(index, value);
		}else {//3
			//max
			max(index, value);
		}
	}
	public void max(int index,Number value) {
		if(index>maxId) {
			return;
		}
		int split = getSplit(index);
		IAggrElement e = splits.get(split);
		ReadWriteLock rwl = e.getLock();
		Lock lock = rwl.writeLock();
		try {
			lock.lock();
			float[] vs = e.getData();
			int i = index%AggrElement.SIZE;
			if(vs[i]<value.floatValue()) {
				vs[i]= value.floatValue();
			}
		} finally {
			lock.unlock();
		}
	}
	public void min(int index,Number value) {
		if(index>maxId) {
			return;
		}
		int split = getSplit(index);
		IAggrElement e = splits.get(split);
		ReadWriteLock rwl = e.getLock();
		Lock lock = rwl.writeLock();
		try {
			lock.lock();
			float[] vs = e.getData();
			int i = index%AggrElement.SIZE;
			if(vs[i]>value.floatValue()) {
				vs[i]= value.floatValue();
			}
		} finally {
			lock.unlock();
		}
	}
	public void sum(int index,Number value) {
		if(index>maxId) {
			return;
		}
		int split = getSplit(index);
		IAggrElement e = splits.get(split);
		ReadWriteLock rwl = e.getLock();
		Lock lock = rwl.writeLock();
		try {
			lock.lock();
			float[] vs = e.getData();
			vs[index%AggrElement.SIZE]+=value.floatValue();
		} finally {
			lock.unlock();
		}
	}
}
