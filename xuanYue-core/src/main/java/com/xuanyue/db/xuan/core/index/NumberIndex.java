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
 * 有符号整数。
 * 这里数值的符号不是用符号位来表示的，因为用符号位来表示数值正负，在排序时会非常复杂，所以这里采用   无符号数值  加  偏移量  的方式。
 * zero： 定义0点坐标。  就是偏移量。
 *        原始值 = data-zero
 * 例如  zero 设置为120（十进制一百）： 
 *      原始值 = 0      data= 120
 *      原始值 = 120    data= 240
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class NumberIndex implements IColumn<Long>{

	
	private long zero;//1:+ 0:-
	private UNumberIndex data;
	
	private long pow(int x,int exponent) {
		long r = 1;
		for(int i=0;i<exponent;i++) {
			r*=x;
		}
		return r;
	}
	
	public IBitIndex getMask() {
		return data.getMask();
	}
	public void flush(int maxId) {
		data.flush(maxId);
	}
	public NumberIndex(int size,int zero) {
		data = new UNumberIndex(size);
		this.zero=zero;
	}
	public NumberIndex(int size) {
		data = new UNumberIndex(size);
		zero=pow(2,size-1)-1;
	}
	public NumberIndex() {}
	@Override
	public void equeals(IBitIndex cache, IBitIndex now, Number valueO) {
		if(valueO==null) {
			cache.copyFrom(getMask());
			cache.not();
			return;
		}
		data.equeals(cache,null, valueO.longValue()+zero);
		cache.and(getMask());
	}

	@Override
	public void greater(IBitIndex cache, IBitIndex now, Number valueO) {
		data.greater(cache, now, valueO.longValue()+zero);
		cache.and(getMask());
	}

	@Override
	public void less(IBitIndex cache, IBitIndex now,  Number valueO) {
		data.less(cache, now, valueO.longValue()+zero);
		cache.and(getMask());
	}

	@Override
	public void greaterAndEq(IBitIndex cache, IBitIndex now,  Number valueO) {
		data.greaterAndEq(cache, now, valueO.longValue()+zero);
		cache.and(getMask());
	}

	@Override
	public void lessAndEq(IBitIndex cache, IBitIndex now,  Number valueO) {
		long value = (long)valueO;
		value+=zero;
		data.lessAndEq(cache, now, value);
		cache.and(getMask());
	}

	@Override
	public void set(int rowId, Object valueO) {
		if(valueO==null) {
			getMask().set(rowId, false);
		}else {
			long value = (long)valueO;
			value+=zero;
			data.set(rowId, value);
		}
		
	}

	@Override
	public Long get(int rowId) {
		if(getMask().get(rowId)) {
			long v = data.get(rowId);
			return v-zero;
		}else {
			return null;
		}
	}

	@Override
	public void expr(String method, Object v,List<IBitIndex> caches) {
		Number value = (Number)v;
		if("!=".equals(method)) {
			this.equeals(caches.get(0),null, value);
			caches.get(0).not();
		}else if("=".equals(method)) {
			this.equeals(caches.get(0),null, value);
		}else if(">=".equals(method)){
			this.greaterAndEq(caches.get(0), caches.get(1), value);
		}else if(">".equals(method)){
			this.greater(caches.get(0), caches.get(1), value);
		}else if("<=".equals(method)){
			this.lessAndEq(caches.get(0), caches.get(1), value);
		}else if("<".equals(method)){
			this.less(caches.get(0), caches.get(1), value);
		}else {
			throw new IndexException(  this.getClass().getName()+ " not support " + method);
		}
	}

	@Override
	public void save(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		Savor.write(zero,String.format("%s/zero",path));
		if(data!=null)
			data.save(String.format("%s/data", path));
	}

	@Override
	public void load(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			throw new IndexException(String.format("%s is not exists", path));
		}
		data.load(String.format("%s/data", path));
		zero = Savor.read(String.format("%s/zero",path));
	}
	@Override
	public void init(String path) throws Exception{
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		data.init(String.format("%s/data", path));
		Savor.write(zero,String.format("%s/zero",path));
	}
	public static void main(String[] args) {
		NumberIndex un = new NumberIndex(10);
		
		int maxId = 10;
		
		un.set(0, 1l);
		un.set(1, 42l);
		un.set(2, -3l);
		un.set(3, 3l);
		un.set(4, 31l);
		un.set(5, -35l);
		un.set(6, 1l);
		un.set(7, 3l);
		un.set(8, 83l);
		un.set(9, 3l);
		un.set(10, 0l);
		un.flush(maxId);
		
		List<IBitIndex> caches = new ArrayList<>();
		
		IBitIndex c1 = new BitIndex();
		c1.flush(maxId);
		caches.add(c1);
		c1 = new BitIndex();
		c1.flush(maxId);
		caches.add(c1);
		un.expr("<", -3l, caches);
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
	public List<ISortElement> getSortE(boolean isDesc,String ...names) {
		return data.getSortE(isDesc);
	}

	@Override
	public byte getType() {
		return 2;
	}

	@Override
	public void saveRow(String path, int rowId) throws Exception {
		data.saveRow(String.format("%s/data", path),rowId);
	}
}
