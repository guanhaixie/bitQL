package com.xuanyue.db.xuan.core.table;

/**
 * 检查
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
public interface ICheckor {
	public boolean check(int maxId);
	public void flush(int maxId);
}
