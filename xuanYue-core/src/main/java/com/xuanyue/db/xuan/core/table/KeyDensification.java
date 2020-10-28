package com.xuanyue.db.xuan.core.table;

import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.xuanyue.db.xuan.core.tools.Savor;

public class KeyDensification implements IPersistence{

	private int[] indexes;
	private AtomicInteger next = new AtomicInteger(1);
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	public int getMapKey(Number data) throws Exception {
		int index = data.intValue();
		if(indexes[index]>0) {
			return indexes[index];
		}else {
			int r;
			Lock lck = lock.writeLock();
			try {
				lck.lock();
				if(indexes[index]>0) {
					return indexes[index];
				}
				r = next.getAndAdd(1);
				indexes[index]=r;
				return r;
			}finally {
				lck.unlock();
			}
		}
	}
	
	
	public int getNextKey(Number data) {
		
		return 1;
	}
	
	@Override
	public void save(String path) throws Exception {
		int len = indexes.length;
		Savor.write(len, String.format("%s/indexes.length",path));
		RandomAccessFile rw = new RandomAccessFile(String.format("%s/data.kd",path),"rw");
		for(int i=0;i<len;i++) {
			rw.writeInt(indexes[i]);
		}
		rw.close();
	}

	@Override
	public void load(String path) throws Exception {
		int len = Savor.read(String.format("%s/indexes.length",path));
		RandomAccessFile rw = new RandomAccessFile(String.format("%s/data.kd",path),"rw");
		for(int i=0;i<len;i++) {
			indexes[i] = rw.readInt();
		}
		rw.close();
	}
}
