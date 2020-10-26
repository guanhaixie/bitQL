package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.ISortElement;
import com.xuanyue.db.xuan.core.table.sort.SortElement;
/**
 * boolean数据列
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class BooleanIndex implements  IColumn<Boolean>{
	private static Logger log= LoggerFactory.getLogger(BooleanIndex.class);
	private IBitIndex data = new BitIndex();
	private IBitIndex mask = new BitIndex();
	
	@Override
	public void flush(int maxId) {
		data.flush(maxId);
		mask.flush(maxId);
	}
	@Override
	public void equeals(IBitIndex cache, IBitIndex now, Number valueO) {
		if(valueO==null) {
			cache.copyFrom(mask);
			cache.not();
			return;
		}
		
		Boolean value = valueO.intValue()==1;
		cache.copyFrom(data);
		if(!value) {
			cache.not();
		}
		cache.and(mask);
	}

	@Override
	public void greater(IBitIndex cache, IBitIndex now,Number valueO) {
		throw new IndexException("BooleanIndex not support greater");
	}

	@Override
	public void less(IBitIndex cache, IBitIndex now,Number valueO) {
		throw new IndexException("BooleanIndex not support less");
	}

	@Override
	public void greaterAndEq(IBitIndex cache, IBitIndex now,Number valueO) {
		throw new IndexException("BooleanIndex not support greaterAndEq");
	}

	@Override
	public void lessAndEq(IBitIndex cache, IBitIndex now,Number valueO) {
		throw new IndexException("BooleanIndex not support lessAndEq");
	}

	@Override
	public void set(int rowId,Object valueO) {
		if(valueO!=null) {
			mask.set(rowId);
			Boolean value = (boolean)valueO;
			data.set(rowId, value);
		}else {
			mask.set(rowId,false);
		}
		
	}

	@Override
	public Boolean get(int rowId) {
		return data.get(rowId);
	}

	@Override
	public void expr(String method, Object value,List<IBitIndex> caches) {
		int v = ( (boolean)value?1:0  );
		if("=".equals(method)) {
			this.equeals(caches.get(0),null, v);
		}else if(">=".equals(method)){
			this.greaterAndEq(caches.get(0), caches.get(1), v);
		}else if(">".equals(method)){
			this.greater(caches.get(0), caches.get(1), v);
		}else if("<=".equals(method)){
			this.lessAndEq(caches.get(0), caches.get(1), v);
		}else if("<".equals(method)){
			this.lessAndEq(caches.get(0), caches.get(1), v);
		}else {
			throw new IndexException("BooleanIndex not support " + method);
		}
	}

	@Override
	public void save(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		data.save(String.format("%s/data", path));
		mask.save(String.format("%s/mask", path));
	}
	public void toBatchLoadMode(String path) {
		data = new BatchBitIndex(String.format("%s/data", path));
		mask = new BatchBitIndex(String.format("%s/mask", path));
	}
	@Override
	public void load(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			throw new IndexException(String.format("%s is not exists", path));
		}
		data.load(String.format("%s/data", path));
		mask.load(String.format("%s/mask", path));
		
	}
	@Override
	public void init(String path) throws Exception{
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		data.init(String.format("%s/data", path));
		mask.init(String.format("%s/mask", path));
	}
	@Override
	public int getDataSize() {
		return 2;
	}
	@Override
	public List<ISortElement> getSortE(boolean isDesc, String... names) {
		List<ISortElement> el = new ArrayList<ISortElement>();
		SortElement e = new SortElement();
		e.setData(mask);
		e.setDesc(isDesc);
		el.add(e);
		
		e = new SortElement();
		e.setData(data);
		e.setDesc(isDesc);
		el.add(e);
		
		return el;
	}
	@Override
	public byte getType() {
		return 4;
	}
	@Override
	public void saveRow(String path,int rowId) throws Exception {
		data.saveRow(String.format("%s/data", path), rowId);
		mask.saveRow(String.format("%s/mask", path), rowId);
	}

	
	
	
}
