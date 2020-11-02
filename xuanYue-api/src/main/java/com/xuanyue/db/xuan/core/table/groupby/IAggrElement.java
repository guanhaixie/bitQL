package com.xuanyue.db.xuan.core.table.groupby;

import java.util.concurrent.locks.ReadWriteLock;

import com.xuanyue.db.xuan.core.table.Source;


public interface IAggrElement extends Source{
	public static final int INI = 1;
	public static final int FLOAT = 2;
	public static final int BYTE = 3;
	public static final int SHORT = 4;
	public static final int SIZE = 1024;
	
	public ReadWriteLock getLock();
	public <T>T getData();

}
