package com.xuanyue.db.xuan.core.table.groupby;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 聚合函数计算内存资源
 * 有4中类型：  int float byte short
 * @author guanh
 *
 */
public class AggrElement implements IAggrElement{
	private Object data;// = new Number[SIZE];
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	public AggrElement(int type) {
		
		switch(type) {
			case 1:
				data = new int[SIZE];
				break;
			case 2:
				data = new float[SIZE];
				break;
			case 3:
				data = new byte[SIZE];
				break;
			case 4:
				data = new short[SIZE];
				break;
		}
	}
	public ReadWriteLock getLock() {
		return lock;
	}
	@SuppressWarnings("unchecked")
	public <T>T getData() {
		return (T)data;
	}

	@Override
	public boolean check(int maxId) {
		return true;
	}

	@Override
	public void flush(int maxId) {
	}
}
