package com.xuanyue.db.xuan.core.table;

import java.util.List;
import java.util.Map;

/**
 * bit 向量
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
public interface IBitIndex extends Source,Cloneable, java.io.Serializable{
	public void flush(int maxId) ;
	public void set(int index,boolean value) ;
	public Map<Integer,IXGHBitSet> getSplits();
	public void set(int index) ;
	public int andByCardinality(IBitIndex set);
	public boolean get(int index) ;
	public void and(IBitIndex set) ;
	public void andNot(IBitIndex set) ;
	public void setAll(boolean value);
	public void orByinnerAndNot(IBitIndex a,IBitIndex b) ;
	public void orByinnerAnd(IBitIndex a,IBitIndex b) ;
	public void copyFrom(IBitIndex sr);
	public List<Integer> limit(int offset,int num);
	public int cardinality();
	public int andNotByCardinality(IBitIndex set);
	public void or(IBitIndex set);
	public void orNot(IBitIndex set);
	public void clearButNumTrue(int count);
	
	public IBitIndex not();
	public void init(String path) throws Exception ;
	public void save(String path) throws Exception ;
	public void saveRow(String path,int rowId)throws Exception;
	public void load(String path) throws Exception;
	
	public List<Integer> getIndexOftrue();
	
	public void xor(IBitIndex set);
	public int nextSetBit(int from);
	public int nextClearBit(int from);
}
