package com.xuanyue.db.xuan.core.table;

public interface ICheckor {
	public boolean check(int maxId);
	public void flush(int maxId);
}
