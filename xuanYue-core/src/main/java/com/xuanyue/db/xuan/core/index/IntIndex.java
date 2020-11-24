package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.util.List;

import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.ISortElement;
import com.xuanyue.db.xuan.msg.VLAUETYPE;
/**
 * 整形
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class IntIndex implements IColumn{
	private NumberIndex data;
	
	public IntIndex(int max) {
		data = new NumberIndex(max);
	}
	
	@Override
	public void save(String path) throws Exception {
		data.save(path);
	}
	@Override
	public void toBatchLoadMode(String path) {
		data.toBatchLoadMode(path);
	}
	@Override
	public void load(String path) throws Exception {
		data.load(path);
	}
	@Override
	public void init(String path) throws Exception{
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		data.init(String.format("%s/data", path));
	}
	@Override
	public void equeals(IBitIndex cache, IBitIndex now, Number value) {
		data.equeals(cache,null, value);
	}

	@Override
	public void greater(IBitIndex cache, IBitIndex now, Number value) {
		data.greater(cache, now, value);
	}

	@Override
	public void less(IBitIndex cache, IBitIndex now, Number value) {
		data.less(cache, now, value);
	}

	@Override
	public void greaterAndEq(IBitIndex cache, IBitIndex now, Number value) {
		data.greaterAndEq(cache, now, value);
	}

	@Override
	public void lessAndEq(IBitIndex cache, IBitIndex now, Number value) {
		data.lessAndEq(cache, now, value);
	}

	@Override
	public void set(int rowId, Object value) {

		if(value!=null) {
			long v=(int)value;
			value = v;
		}
		data.set(rowId, value);
	}

	@Override
	public Integer get(int rowId) {
		// TODO Auto-generated method stub
		Long v = data.get(rowId);
		if(v==null) {
			return null;
		}else {
			return v.intValue();
		}
	}

	@Override
	public void expr(String method, Object value, List<IBitIndex> caches) {
		data.expr(method, value, caches);
	}

	@Override
	public void flush(int maxId) {
		data.flush(maxId);
	}

	@Override
	public int getDataSize() {
		return data.getDataSize();
	}

	@Override
	public List<ISortElement> getSortE(boolean isDesc, String... names) {
		return data.getSortE(isDesc);
	}
	@Override
	public boolean checkSortE(boolean isDesc,String names) {
		return true&&names==null;
	}
	@Override
	public VLAUETYPE getType() {
		return VLAUETYPE.INT;
	}

	@Override
	public void saveRow(String path, int rowId) throws Exception {
		data.saveRow(path, rowId);
	}
}
