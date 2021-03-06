package com.xuanyue.db.xuan.core.table;

import java.util.List;

import com.xuanyue.db.xuan.msg.VLAUETYPE;

/**
 * 列接口
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
public interface IColumn extends IPersistence{

	/**= 等*/
	public void equeals(IBitIndex cache, IBitIndex now,Number value);
	/** > 大于*/
	public void greater(IBitIndex cache,IBitIndex now,Number value);
	/** < */
	public void less(IBitIndex cache,IBitIndex now,Number value);
	/** >=*/
	public void greaterAndEq(IBitIndex cache,IBitIndex now,Number value);
	/** <= */
	public void lessAndEq(IBitIndex cache,IBitIndex now,Number value);
	/**添加或更新*/
	public void set(int rowId,Object value);
	/**持久化一行数据库*/
	public void saveRow(String path,int rowId)throws Exception;
	/**读取数据*/
	public Object get(int rowId);
	/**通用的逻辑表达式*/  
	public void expr(String method,Object value,List<IBitIndex> caches);
	/**
	 * 检查逻辑运算表达式是否有语法错误
	 * @param fieldName
	 * @param op
	 * @param v
	 * @param caches
	 * @return
	 */
	public int checkExpr(String op,Object v);
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
	 * 检查列排序是否支持
	 * @param isDesc
	 * @param names
	 * @return
	 */
	public boolean checkSortE(boolean isDesc,String names);
	/**
	 * 数据类型
	 * @return
	 */
	public VLAUETYPE getType();
	
	public void init(String path)throws Exception;
	
	public void toBatchLoadMode(String path);
	
}
