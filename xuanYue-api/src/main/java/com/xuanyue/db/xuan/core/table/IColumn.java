package com.xuanyue.db.xuan.core.table;

import java.util.List;

/**
 * 列接口
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
public interface IColumn<T> extends IPersistence{

	//= 等
	public void equeals(IBitIndex cache, IBitIndex now,Number value);
	// > 大于
	public void greater(IBitIndex cache,IBitIndex now,Number value);
	// <
	public void less(IBitIndex cache,IBitIndex now,Number value);
	//>=
	public void greaterAndEq(IBitIndex cache,IBitIndex now,Number value);
	//<=
	public void lessAndEq(IBitIndex cache,IBitIndex now,Number value);
	//添加或更新
	public void set(int rowId,Object value);
	public void saveRow(String path,int rowId)throws Exception;
	//读取数据
	public T get(int rowId);
	//通用的逻辑表达式  
	public void expr(String method,Object value,List<IBitIndex> caches);
	/**
	 * 刷新最大ID
	 * @param maxId
	 */
	public void flush(int maxId);
	/**
	 * 数据列占用的bit向量个数
	 * @return
	 */
	public int getDataSize();
	/**
	 * 数据的bit向量封装为排序单元。
	 * @param isDesc  
	 * @param names  针对 Mapboolean，或者MapUNumber的情况，需要按照其指定的key排序。
	 * @return
	 */
	public List<ISortElement> getSortE(boolean isDesc,String ...names);
	/**
	 *case 0://int
	 *case 1://float
	 *case 2://long
	 *case 3://Date
	 *case 4://boolean
	 *case 5://MapBoolean 
	 *case 6://MapNum
	 *case 7://List<String>
	 * @return
	 */
	public byte getType();
	
	public void init(String path)throws Exception;
}
