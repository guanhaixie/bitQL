package com.xuanyue.db.xuan.core.db;

import com.xuanyue.db.xuan.core.table.IXyTable;

public interface IXyDB {

	public void init(DBMeta dbMeta) throws Exception;
	public void load()throws Exception;
	public IXyTable getTable(String name);
}
