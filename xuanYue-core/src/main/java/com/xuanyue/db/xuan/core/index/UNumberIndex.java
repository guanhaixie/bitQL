package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.ISortElement;
import com.xuanyue.db.xuan.core.table.sort.SortElement;
/**
 * 无符号整形
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class UNumberIndex implements IColumn{
	private HashMap<Integer,IBitIndex> positionV2bit = new HashMap<>();
	private IBitIndex mask = new BitIndex();
	private int maxBit=44;
	public IBitIndex getMask() {
		return mask;
	}
	public UNumberIndex(int max) {
		super();
		this.maxBit = max;
		init();
	}
	public UNumberIndex() {}
	public void flush(int maxId) {
		mask.flush(maxId);
		positionV2bit.forEach((k,v)->{
			v.flush(maxId);
		});
	}
	
	public void init() {
		for(int i=0;i<maxBit;i++) {
			positionV2bit.put(i, new BitIndex());
		}
	}
	@Override
	public void save(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		mask.save(String.format("%s/mask", path));
		for(Entry<Integer,IBitIndex> en:positionV2bit.entrySet()) {
			en.getValue().save(String.format("%s/positionV2bit/%s", path,en.getKey()));
		} 
	}
	@Override
	public void toBatchLoadMode(String path) {
		mask = new BatchBitIndex(String.format("%s/mask", path));
		for(Entry<Integer,IBitIndex> en:positionV2bit.entrySet()) {
			positionV2bit.put(en.getKey(), new BatchBitIndex(String.format("%s/positionV2bit/%s", path,en.getKey()) )  );
		}
	}
	@Override
	public void load(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			throw new IndexException(String.format("%s is not exists", path));
		}
		mask = new BitIndex();
		mask.load(String.format("%s/mask", path));
		File pvs = new File(String.format("%s/positionV2bit", path));
		IBitIndex posi = null;
		positionV2bit.clear();
		for(File pv:pvs.listFiles()) {
			posi = new BitIndex();
			posi.load(String.format("%s/positionV2bit/%s",path, pv.getName()));
			positionV2bit.put(Integer.parseInt(pv.getName()), posi);
		}
		this.maxBit = positionV2bit.size();
	}
	@Override
	public void init(String path) throws Exception{
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		mask.init(String.format("%s/mask", path));
		File pvs = new File(String.format("%s/positionV2bit", path));
		pvs.mkdirs();
		for(Entry<Integer,IBitIndex> en:positionV2bit.entrySet()) {
			en.getValue().init(String.format("%s/positionV2bit/%s", path,en.getKey()));
		}
	}
	@Override
	public void equeals(IBitIndex cache, IBitIndex now,Number v) {
		if(v==null) {
			cache.copyFrom(mask);
			cache.not();
			return;
		}
		long value = v.longValue();
		cache.setAll(true);
		long t = 0;
		for(int i=0;i<this.maxBit;i++) {
			t=(1l<<i);
			if((value&t)==t) {
				cache.and(positionV2bit.get(i));
			}else {
				cache.andNot(positionV2bit.get(i));
			}
		}
		cache.and(mask);
	}
	
	private void handleGreater(IBitIndex cache,IBitIndex now, long value) {
		long t = 0;
		now.setAll(true);
		cache.setAll(false);
		for(int i=this.maxBit-1;i>=0;i--) {
			t=(1l<<i);
			if((value&t)==t) {
				now.and(positionV2bit.get(i));
			}else {
				cache.orByinnerAnd(now, positionV2bit.get(i));
				now.andNot( positionV2bit.get(i) );
			}
		}
	}
	
	@Override
	public void greater(IBitIndex cache,IBitIndex now, Number v) {
		handleGreater(cache, now, v.longValue());
		cache.and(mask);
	}

	private void handleLess(IBitIndex cache, IBitIndex now, long value) {
		long t;
		now.setAll(true);
		cache.setAll(false);
		for(int i=this.maxBit-1;i>=0;i--) {
			t=(1l<<i);
			if((value&t)==t) {
				cache.orByinnerAndNot(now, positionV2bit.get(i));
				now.and(positionV2bit.get(i));
			}else {
				now.andNot( positionV2bit.get(i) );
			}
		}
	}
	
	@Override
	public void less(IBitIndex cache, IBitIndex now, Number value) {
		handleLess(cache, now, value.longValue());
		cache.and(mask);
	}

	@Override
	public void greaterAndEq(IBitIndex cache, IBitIndex now, Number value) {
		this.handleGreater(cache, now, value.longValue());
		cache.or(now);
		cache.and(mask);
	}

	@Override
	public void lessAndEq(IBitIndex cache, IBitIndex now,Number value) {
		this.handleLess(cache, now, value.longValue());
		cache.or(now);
		cache.and(mask);
	}

	@Override
	public void set(int rowId,Object valueO) {
		if(valueO==null) {
			mask.set(rowId, false);
		}else {
			mask.set(rowId);
			long value = ( valueO instanceof Integer?(int)valueO: (
					valueO instanceof String?Long.valueOf(valueO.toString()) :   (long)valueO  )
				
					);
			long t = 0;
			for(int i=0;i<this.maxBit;i++) {
				t=(1l<<i);
				positionV2bit.get(i).set(rowId,((value&t)==t));
			}
		}
	}

	@Override
	public Long get(int rowId) {
		if(mask.get(rowId)) {
			long v = 0;
			for(int i=0;i<maxBit;i++) {
				if(positionV2bit.get(i).get(rowId)) {
					v +=(1l<<i);
				}
			}
			return v;
		}else {
			return null;
		}
		
	}

	@Override
	public void expr(String method, Object valueO, List<IBitIndex> caches) {
		Number value = null;
		if(valueO!=null) {
			if(valueO instanceof Integer) {
				value = (int)valueO;
			}else if(valueO instanceof Long) {
				value = (long)valueO;
			}
		}
		System.out.println(String.format("data %s %s", method,value));
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
	public static void main(String[] args) {
		UNumberIndex un = new UNumberIndex(10);
		int maxId = 10;
		un.set(0, 1l);
		un.set(1, 42l);
		un.set(2, 3l);
		un.set(3, 3l);
		un.set(4, 31l);
		un.set(5, 3l);
		un.set(6, 1l);
		un.set(7, 3l);
		un.set(8, 83l);
		un.set(9, 3l);
		un.flush(maxId);
		
		List<IBitIndex> caches = new ArrayList<>();
		
		IBitIndex c1 = new BitIndex();
		c1.flush(maxId);
		caches.add(c1);
		c1 = new BitIndex();
		c1.flush(maxId);
		caches.add(c1);
		un.expr("!=", 3l, caches);
		Set<Integer> ids = new HashSet<>(caches.get(0).getIndexOftrue());
		for(int i=0;i<10;i++) {
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
		return positionV2bit.size()+1;
	}
	@Override
	public List<ISortElement> getSortE(boolean isDesc,String ...names) {
		// TODO Auto-generated method stub
		List<ISortElement> r = new ArrayList<>();
		for(int i=maxBit-1;i>-1;i--) {
			SortElement e = new SortElement();
			e.setData(positionV2bit.get(i));
			e.setDesc(isDesc);
			r.add(e);
		}
		return r;
	}
	@Override
	public byte getType() {
		return 2;
	}
	@Override
	public void saveRow(String path, int rowId) throws Exception {
		mask.saveRow(String.format("%s/mask", path),rowId);
		for(Entry<Integer,IBitIndex> en:positionV2bit.entrySet()) {
			en.getValue().saveRow(String.format("%s/positionV2bit/%s", path,en.getKey()),rowId);
		}
	}
	
}
