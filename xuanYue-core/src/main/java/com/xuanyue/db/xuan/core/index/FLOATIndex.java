package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.ISortElement;
import com.xuanyue.db.xuan.core.tools.Savor;
/**
 * 浮点型
 * 真实值 = ((float)持久化值) /eNum
 * 例如    eNum设置为100,  
 *    一个数 12.2  的  持久化值为    (long)(12.2 * 100)  即 1220
 *    
 * 浮点型数据列的存储本质上是一个有符号整形，
 * eNum 表示数据位移，需要是10的N次方。
 * 
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class FLOATIndex implements IColumn<Float>{

	private NumberIndex data;
	private int eNum=10000;
	
	public FLOATIndex(int size,int eNum) {
		data = new NumberIndex(size);
		this.eNum=eNum;
	}
	public FLOATIndex(int size,int eNum,int zero) {
		data = new NumberIndex(size,zero);
		this.eNum=eNum;
	}
	public void flush(int maxId) {
		data.flush(maxId);
	}
	@Override
	public void equeals(IBitIndex cache, IBitIndex now, Number valueO) {
		if(valueO==null) {
			cache.copyFrom(data.getMask());
			cache.not();
			return;
		}
		Float value = valueO.floatValue();
		value*=eNum;
		data.equeals(cache,null, value.longValue());
	}
	@Override
	public void greater(IBitIndex cache, IBitIndex now, Number valueO) {
		Float value = valueO.floatValue();
		value*=eNum;
		data.greater(cache, now, value.longValue());
	}
	@Override
	public void less(IBitIndex cache, IBitIndex now, Number valueO) {
		Float value = valueO.floatValue();
		value*=eNum;
		data.less(cache, now, value.longValue());
	}
	@Override
	public void greaterAndEq(IBitIndex cache, IBitIndex now, Number valueO) {
		Float value = valueO.floatValue();
		value*=eNum;
		data.greaterAndEq(cache, now, value.longValue());
	}
	@Override
	public void lessAndEq(IBitIndex cache, IBitIndex now, Number valueO) {
		Float value = valueO.floatValue();
		value*=eNum;
		data.lessAndEq(cache, now, value.longValue());
	}
	@Override
	public void set(int rowId,Object valueO) {
		if(valueO==null) {
			data.set(rowId,null);
			return;
		}
		Float value = ((Number)valueO).floatValue();
		value*=eNum;
		data.set(rowId, value.longValue());
	}
	@Override
	public Float get(int rowId) {
		Long v = data.get(rowId);
		if(v==null) {
			return null;
		}
		return v.floatValue()/eNum;
	}
	@Override
	public void expr(String method, Object value,List<IBitIndex> caches) {
		Number v = (Number)value;
		if("!=".equals(method)) {
			this.equeals(caches.get(0),null, v);
			caches.get(0).not();
		}else if("=".equals(method)) {
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
			throw new IndexException("FloatIndex not support " + method);
		}
	}

	@Override
	public void save(String path) throws Exception {
		data.save(String.format("%s/data", path));
		Savor.write(eNum, String.format("%s/eNum",path));
	}

	@Override
	public void load(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			throw new IndexException(String.format("%s is not exists", path));
		}
		data.load(String.format("%s/data", path));
		eNum = Savor.read(String.format("%s/eNum",path));
	}
	@Override
	public void init(String path) throws Exception{
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		data.init(String.format("%s/data", path));
		Savor.write(eNum, String.format("%s/eNum",path));
	}
	public static void main(String[] args) {
		FLOATIndex un = new FLOATIndex(32,1000);
		
		int maxId = 10;
		
		un.set(0, 1f);
		un.set(1, 42f);
		un.set(2, -3f);
		un.set(3, 3f);
		un.set(4, 31f);
		un.set(5, -35f);
		un.set(6, 0f);
		un.set(7, 3f);
		un.set(8, 83.1234f);
		un.set(9, 3.23f);
		un.set(10, 0f);
		
		un.flush(maxId);
		
		List<IBitIndex> caches = new ArrayList<>();
		
		IBitIndex c1 = new BitIndex();
		c1.flush(maxId);
		caches.add(c1);
		c1 = new BitIndex();
		c1.flush(maxId);
		caches.add(c1);
		un.expr("=", 0f, caches);
		Set<Integer> ids = new HashSet<>(caches.get(0).getIndexOftrue());
		for(int i=0;i<11;i++) {
			System.out.println(String.format("%s/%s%s%s",
					i,
					(ids.contains(i)?"[":" "), 
					un.get(i),
					ids.contains(i)?"]":" "));
		}
		System.out.println("*****************");
	}
	@Override
	public int getDataSize() {
		return data.getDataSize();
	}
	@Override
	public List<ISortElement> getSortE(boolean isDesc, String... names) {
		return data.getSortE(isDesc, names);
	}
	@Override
	public byte getType() {
		return 1;
	}
	@Override
	public void saveRow(String path, int rowId) throws Exception {
		data.saveRow(String.format("%s/data", path), rowId);
	}
}
