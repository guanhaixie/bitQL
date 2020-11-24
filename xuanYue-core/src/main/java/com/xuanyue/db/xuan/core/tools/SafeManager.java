package com.xuanyue.db.xuan.core.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.xuanyue.db.xuan.core.exception.BankerException;
import com.xuanyue.db.xuan.core.table.Source;


/**
 * 银行家算法的简化版：
 * 简单资源管理：
 * 每次申请最大资源使用量，分配资源时做死锁检查。
 * 释放时也是全部释放
 * 
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class SafeManager<T extends Source> {

	private int size;
	private int surplus;
	
	private final Lock mLock = new ReentrantLock();
	private Condition safe = mLock.newCondition();
	
	private ObjectPool<T> objectPool;
	
	public SafeManager(BasePooledObjectFactory<T> f,int size) {
		this.size = size;
		this.surplus = size;
		GenericObjectPoolConfig<T> c = new GenericObjectPoolConfig<>();
		c.setMaxTotal(size);
		c.setTestOnReturn(true);
		c.setTestOnBorrow(true);
		//f.setMaxId(maxId);
		objectPool = new GenericObjectPool<T>(f,c);
	}
	
	public List<T> apply(int x) {
		if(x>size) {
			throw new BankerException(String.format("apply num %s is greater than SafeManager size:%s", x,size));
		}
		try {
			mLock.lock();
			
			while(true) {
				if(surplus>=x) {
					surplus-=x;
					List<T> r = new ArrayList<>();
					
					for(int i=0;i<x;i++) {
						r.add(objectPool.borrowObject());
					}
					return r;
				}else{
					safe.await();
				}
			}
		} catch (Exception e) {
			throw new BankerException(e);
		} finally {
			mLock.unlock();
		}
	}
	public void returnSource(List<T> sources) {
		try {
			mLock.lock();
			surplus+=sources.size();
			sources.forEach(e->{
				try {
					objectPool.returnObject(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			safe.signalAll();
		} finally {
			mLock.unlock();
		}
	}

}
