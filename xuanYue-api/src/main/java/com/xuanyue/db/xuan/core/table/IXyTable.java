package com.xuanyue.db.xuan.core.table;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 数据表接口
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
public interface IXyTable extends IPersistence{

	/**
	 * 行掩码，用于软删除。
	 * @return
	 */
	public IBitIndex getMask();
	/**
	 * 表名
	 * @return
	 */
	public String getName();
	/**
	 * 列信息
	 * @return
	 */
	public Map<String,IColumn<?>> getName2column();
	/**
	 * 添加一列
	 * @param name
	 * @param column
	 */
	public void addColumn(String name,IColumn<?> column);
	/**
	 * 申请资源
	 * 简单银行家算法：
	 * 一次性申请最大使用资源
	 * @param maxSouce
	 * @return
	 * @throws NoSuchElementException
	 * @throws IllegalStateException
	 * @throws Exception
	 */
	public List<IBitIndex> apply(int maxSouce) throws NoSuchElementException, IllegalStateException, Exception;
	/**
	 * 返回资源
	 * @param sources
	 */
	public void returnSource(List<IBitIndex> sources);
	/**
	 * 逻辑运算
	 * @param fieldName 列名称
	 * @param op   >,>=,<,<=,=,!=
	 * @param v  值
	 * @param caches 计算时需要的内存缓冲
	 */
	public void expr(String fieldName,String op,Object v,List<IBitIndex> caches);
	/**
	 * 插入
	 * @param vs
	 * @return
	 */
	public int insertInto(Map<String,Object> vs);
	/**
	 * 刷新rowId
	 * @param rowId
	 */
	public void flush(int rowId);
	/**
	 * 读取数据
	 * @param rowId
	 * @return
	 */
	public Map<String,Object> read(int rowId);
	/**
	 * 统计表中bit向量的个数。
	 * @return
	 */
	public int getDataSize();
	/**
	 * 列属性
	 * @param fn
	 * @return
	 */
	public byte getType(String fn);
	/**
	 * bit向量封装为排序单元并返回。
	 * @param fn
	 * @param isDesc
	 * @param key
	 * @return
	 */
	public List<ISortElement> getSortE(String fn,boolean isDesc,String key);
	/**
	 * 初始化
	 * @param path
	 * @throws Exception
	 */
	public void init(String path)throws Exception;
	/**
	 * 保存一行
	 * @param path
	 * @param rowId
	 * @throws Exception
	 */
	public void saveRow(String path,int rowId)throws Exception;
	
	
	public void toBatchLoadMode(String path);
	
}
