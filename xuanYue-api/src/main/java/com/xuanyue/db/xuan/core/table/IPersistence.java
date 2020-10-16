package com.xuanyue.db.xuan.core.table;

/**
 * 索引持久化
 * @author guanh
 *
 */
public interface IPersistence {

	/**
	 * 数据持久化
	 * @param path
	 * @throws Exception
	 */
	public void save(String path)throws Exception;
	/**
	 * 数据加载
	 * @param path
	 * @throws Exception
	 */
	public void load(String path)throws Exception;
}
