package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IXGHBitSet;
import com.xuanyue.db.xuan.core.tools.Savor;
/**
 * bit向量
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class BitIndex implements IBitIndex{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AtomicLong version = new AtomicLong();
	private Map<Integer,IXGHBitSet> splits = new HashMap<>();
	private int maxId;
	public Map<Integer,IXGHBitSet> getSplits(){
		return splits;
	}
	@Override
	public boolean check(int maxId) {
		return this.maxId==maxId;
	}
	public static int getSplit(int index) {
		return index/XGHBitSet.SET_SIZE;
	}
	@Override
	public synchronized void flush(int maxId) {
		if(this.maxId < maxId) {
			this.maxId = maxId;
			int split = getSplit(maxId);
			
	 		for(int i=split;i>=0;i--) {
				if(!splits.containsKey(i)) {
					splits.put(i, new XGHBitSet());
				}
				if(i==split) {
					splits.get(i).flush(maxId%XGHBitSet.SET_SIZE);
				}else {
					splits.get(i).flush(XGHBitSet.SET_SIZE-1);
				}
			}
		}
	}
	
	public void set(int index,boolean value) {
		int split = getSplit(index);
		if(!splits.containsKey(split)) {
			splits.put(split, new XGHBitSet());
		}
		splits.get(split).set(index%XGHBitSet.SET_SIZE, value);
		version.addAndGet(1);
	}
	
	public void set(int index) {
		int split = getSplit(index);
		if(!splits.containsKey(split)) {
			splits.put(split, new XGHBitSet());
		}
		splits.get(split).set(index%XGHBitSet.SET_SIZE);
		version.addAndGet(1);
	}
	
	public boolean get(int index) {
		if(index>maxId) {
			return false;
		}
		int split = getSplit(index);
		return splits.containsKey(split)&&splits.get(split).get(index%XGHBitSet.SET_SIZE);
	}
	public void and(IBitIndex set) {
		splits.forEach((k,v)->{
			v.and(set.getSplits().get(k));
		});
	}
	public void andNot(IBitIndex set) {
		splits.forEach((k,v)->{
			v.andNot(set.getSplits().get(k));
		});
	}
	public void setAll(boolean value) {
		splits.forEach((k,v)->{
			v.setAll(value);
		});
	}
	public void orByinnerAndNot(IBitIndex a,IBitIndex b) {
		splits.forEach( (k,v)->{
			v.orByinnerAndNot(a.getSplits().get(k), b.getSplits().get(k));
		});
	}
	
	public void copyFrom(IBitIndex sr) {
		splits.forEach((k,v)->{
			v.copyFrom(sr.getSplits().get(k));
		});
	}
	
	public int cardinality() {
		int cardinality = 0;
		for(IXGHBitSet e:splits.values()) {
			cardinality+=e.cardinality();
		}
		return cardinality;
	}
	public int andNotByCardinality(IBitIndex set) {
		int cardinality = 0;
		for(Entry<Integer,IXGHBitSet> en:this.splits.entrySet()) {
			
			cardinality+= en.getValue().andNotByCardinality(set.getSplits().get(en.getKey()));
		}
		return cardinality;
	}
	public int andByCardinality(IBitIndex set) {
		int cardinality = 0;
		for(Entry<Integer,IXGHBitSet> en:this.splits.entrySet()) {
			cardinality+= en.getValue().andByCardinality(set.getSplits().get(en.getKey()));
		}
		return cardinality;
	}
	public void or(IBitIndex set) {
		splits.forEach((k,v)->{
			v.or(set.getSplits().get(k));
 		});
	}
	public void orNot(IBitIndex set) {
		splits.forEach((k,v)->{
			v.orNot(set.getSplits().get(k));
 		});
	}
	public void orByinnerAnd(IBitIndex a,IBitIndex b) {
		splits.forEach( (k,v)->{
			v.orByinnerAnd(a.getSplits().get(k), b.getSplits().get(k));
		});
	}
	public void clearButNumTrue(int count) {
		
		int c = 0;
		IXGHBitSet bs = null;
		int split = getSplit(maxId);
		for(int i=0;i<=split;i++) {
			bs = this.splits.get(i);
			if(bs==null) {
				continue;
			}
			if(count<=0) {
				bs.setAll(false);
			}else {
				c = bs.cardinality();
				if(count-c>=0) {
					count -= c;
					continue;
				}else {
					for (int j = bs.nextSetBit(0); j >= 0; j = bs.nextSetBit(j+1)) {
						if(count>0) {
							count--;
						}else {
							bs.set(j, false);
						}
					}
				}
			}
		}
	}
	
	public IBitIndex not() {
		splits.values().forEach((e)-> e.not() );
		return this;
	}
	public void saveV1(String path) throws Exception {
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		new File(String.format("%s/splits", path)).mkdirs();
		int splitSize = XGHBitSet.size();
		byte[] cache = new byte[splitSize];
		int maxSplit = getSplit(maxId);
		for(int i=0;i<=maxSplit;i++) {
			RandomAccessFile rw = new RandomAccessFile(String.format("%s/splits/%s", path,i),"rw");
			if(splits.containsKey(i) ) {
				rw.write(splits.get(i).toByteArray(cache));
			}else {
				for(int j=0;j<splitSize;j++) {//重置为0
					cache[i]=0;
				}
				rw.write(cache);
			}
			rw.close();
		}
		Savor.write(maxId, String.format("%s/maxId",path));
	}
	@Override
	public void save(String path) throws Exception {
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		int splitSize = XGHBitSet.size();
		byte[] cache = new byte[splitSize];
		int maxSplit = getSplit(maxId);
		RandomAccessFile rw = new RandomAccessFile(String.format("%s/xyy.sun", path),"rw");
		for(int i=0;i<=maxSplit;i++) {
			rw.seek(i*cache.length);
			if(splits.containsKey(i) ) {
				rw.write(splits.get(i).toByteArray(cache));
			}else {
				for(int j=0;j<splitSize;j++) {//重置为0
					cache[i]=0;
				}
				rw.write(cache);
			}
		}
		rw.close();
		Savor.write(maxId, String.format("%s/maxId",path));
	}
	@Override
	public void saveRow(String path,int rowId) throws Exception{
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		
		int split = getSplit(rowId);
		long word = splits.get(split).getWord(rowId%XGHBitSet.SET_SIZE);
		RandomAccessFile rw = new RandomAccessFile(String.format("%s/xyy.sun", path),"rw");
		rw.seek( 8*(rowId/64) );
		byte[] result=new byte[8];
		XGHBitSet.longToByteArray(result, word, 0);
		rw.write( result );
	}
	
	public void loadV1(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			throw new IndexException(String.format("%s is not exists", path));
		}
		p=new File(String.format("%s/splits", path));
		byte[] cache = new byte[XGHBitSet.size()];
		for(File s:p.listFiles()) {
			RandomAccessFile rw = new RandomAccessFile(s,"r");
			rw.read(cache);
			splits.put(Integer.parseInt( s.getName()  ), new XGHBitSet(cache));
			rw.close();
		}
		maxId = Savor.read(String.format("%s/maxId",path));
		this.flush(maxId);
	}
	
	@Override
	public void load(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			throw new IndexException(String.format("%s is not exists", path));
		}
		maxId = Savor.read(String.format("%s/maxId",path));
		int maxSplit = getSplit(maxId);
		byte[] cache = new byte[XGHBitSet.size()];
		RandomAccessFile r = new RandomAccessFile(String.format("%s/xyy.sun", path),"r");
		for(int i=0;i<=maxSplit;i++) {
			r.seek(i*cache.length);
			r.read(cache);
			splits.put(i, new XGHBitSet(cache, i==maxSplit?(maxId%XGHBitSet.SET_SIZE  ):(XGHBitSet.SET_SIZE-1)  ));
		}
		r.close();
		this.flush(maxId);
	}
	@Override
	public void init(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		Savor.write(maxId, String.format("%s/maxId",path));
		p = new File(String.format("%s/xyy.sun", path));
		if(!p.exists()) {
			p.createNewFile();
		}
	}
	public List<Integer> getIndexOftrue() {
		List<Integer> r = new ArrayList<Integer>();
		splits.forEach((k,v)->{
			v.getIndexOftrue().forEach((e)-> r.add( k*XGHBitSet.SET_SIZE+e ) );
		});
		return r;
		
	}
	public List<Integer> limit(int offset,int num){
		List<Integer> r = new ArrayList<Integer>();
		int split = getSplit(maxId);
		int c = 0;
		for(int i=0;i<=split;i++) {
			c = splits.get(i).cardinality();
			if(c<=offset) {
				offset-=c;
			}else {
				for (int j = this.nextSetBit(XGHBitSet.SET_SIZE*i); j >= 0; j = this.nextSetBit(j+1)) {
					if(offset<=0) {
						r.add(j);
						num--;
					}
					offset--;
					if(num<=0) {
						return r;
					}
				}
			}
		}
		return r;
	}
	public void xor(IBitIndex set) {
		this.splits.forEach((k,v)->{ v.xor(set.getSplits().get(k)); });
	}
	public int nextSetBit(int from) {
		if(from>maxId) {
			return -1;
		}
		int start = getSplit(from);
		if(!splits.containsKey(start)) {
			return -1;
		}
		int r = splits.get(start).nextSetBit(from%XGHBitSet.SET_SIZE);
		if(r!=-1) {
			return r+start*XGHBitSet.SET_SIZE;
		}
		int split = getSplit(maxId);
		for(int i=start+1;i<=split;i++) {
			r = splits.get(i).nextSetBit(0);
			if(r!=-1) {
				return r+i*XGHBitSet.SET_SIZE;
			}
		}
		return -1;
	}
	public int nextClearBit(int from) {
		if(maxId==0||from>maxId) {
			return -1;
		}
		int start = getSplit(from);
		int r = splits.get(start).nextClearBit(from%XGHBitSet.SET_SIZE);
		if(r!=-1) {
			return r+start*XGHBitSet.SET_SIZE;
		}
		int split = getSplit(maxId);
		for(int i=start+1;i<=split;i++) {
			r = splits.get(i).nextClearBit(0);
			if(r!=-1) {
				return r+i*XGHBitSet.SET_SIZE;
			}
		}
		return -1;
	}
}
