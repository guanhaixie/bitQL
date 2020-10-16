package com.xuanyue.db.xuan.core.db;

import com.xuanyue.db.xuan.core.table.IXyTable;
/**
 * 库接口
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
public interface IXyDB {

	public void init(DBMeta dbMeta) throws Exception;
	public void load()throws Exception;
	public IXyTable getTable(String name);
}
