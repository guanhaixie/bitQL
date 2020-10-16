package com.xuanyue.db.xuan.core.table;

/**
 * 排序最小单元
 * 数据来源是列的原始数据其中一个bit向量，
 * 当排序时会把原始数据包装成一组ISortElement，排序过程不改变原始数据本身
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
public interface ISortElement {
	public IBitIndex getData();
	public boolean isDesc() ;
	public void setData(IBitIndex data);
	public void setDesc(boolean desc);
}
