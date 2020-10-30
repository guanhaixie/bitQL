package com.xuanyue.db.xuan.core.table;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.index.BatchBitIndex;
import com.xuanyue.db.xuan.core.index.BitIndex;
import com.xuanyue.db.xuan.core.tools.SafeManager;
import com.xuanyue.db.xuan.core.tools.Savor;

/**
 * 主表数据
 * 
 * 警告：
 * 写入暂时不提供并发性，当前是串行写入
 * @author guanh
 */
public class XyTable implements IXyTable{
	private static Logger log = LoggerFactory.getLogger(XyTable.class);
	private String name;
	private Map<String,IColumn> name2column = new HashMap<>();
	private Map<Integer,ReadWriteLock> locks = new HashMap<>();
	private AtomicInteger maxId = new AtomicInteger();
	private SafeManager<IBitIndex> manager;
	private IBitIndex mask = new BitIndex();
	public XyTable(String name) {
		this(80,name);
	}
	public IBitIndex getMask() {
		return mask;
	}
	public XyTable(int querySource,String name) {
		this.name = name;
		BitIndexFactory f = new BitIndexFactory();
		f.setMaxId(maxId);
		manager = new SafeManager<IBitIndex>(f,querySource);
	}
	public void toBatchLoadMode(String path) {
		mask = new BatchBitIndex(String.format("%s/mask",path));
		for(Entry<String,IColumn> en:name2column.entrySet()) {
			en.getValue().toBatchLoadMode(String.format("%s/%s", path,en.getKey()));
		}
	}
	private ReadWriteLock getReadWriteLock(int index) {
		int split = index/64000;
		if(!locks.containsKey(split)) {
			synchronized (locks) {
				if(!locks.containsKey(split)) {
					locks.put(split, new ReentrantReadWriteLock());
				}
			}
		}
		return locks.get(split);
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public Map<String,IColumn> getName2column(){
		return name2column;
	}
	@Override
	public void addColumn(String name,IColumn column) {
		name2column.put(name.toLowerCase(), column);
	}
	@Override
	public List<IBitIndex> apply(int maxSouce) throws NoSuchElementException, IllegalStateException, Exception{
		return manager.apply(maxSouce);
	}
	@Override
	public void returnSource(List<IBitIndex> sources){
		manager.returnSource(sources);
	}
	@Override
	public void expr(String fieldName,String op,Object v,List<IBitIndex> caches) {
		log.debug(String.format(" %s %s %s",fieldName,op,v ));
		IColumn c = name2column.get(fieldName.toLowerCase());
		c.expr(op, v, caches);
	}
	private int nextRowId() {
		return maxId.getAndAdd(1);
	}
	@Override
	public int insertInto(Map<String,Object> vs) {
		if(vs==null) {
			throw new IndexException("insertInto value is null");
		}
		int rowId = nextRowId();
		ReadWriteLock rwl = this.getReadWriteLock(rowId);
		Lock lock = rwl.writeLock();
		try {
			lock.lock();
			name2column.forEach( (k,v)->{
				v.set(rowId, vs.get(k));
				v.flush(rowId);
			});
			mask.set(rowId);
			mask.flush(rowId);
		} finally {
			lock.unlock();
		}
		
		return rowId;
	}
	@Override
	public void flush(int rowId) {
		name2column.forEach( (k,v)->{
			v.flush(rowId);
		} );
		mask.set(rowId);
		mask.flush(rowId);
	}
	@Override
	public Map<String,Object> read(int rowId){
		if(!mask.get(rowId)) {
			return null;
		}
		Map<String,Object> r = new HashMap<>();
		name2column.forEach( (k,v)->{
			r.put(k, v.get(rowId));
		} );
		r.put("rowid", rowId);
		return r;
	}
	@Override
	public int getDataSize() {
		int r =0;
		for(Entry<String,IColumn> en:name2column.entrySet()) {
			r+=en.getValue().getDataSize();
		}
		return r;
	}
	
	public int getMaxId() {
		return maxId.get();
	}
	@Override
	public synchronized void save(String path) throws Exception {
		for(Entry<String,IColumn> en:name2column.entrySet()) {
			en.getValue().save(String.format("%s/%s", path,en.getKey()));
		}
		Savor.write(maxId, String.format("%s/maxId",path));
		mask.save(String.format("%s/mask",path));
	}
	public synchronized void saveRow(String path,int rowId)throws Exception{
		for(Entry<String,IColumn> en:name2column.entrySet()) {
			en.getValue().saveRow(String.format("%s/%s", path,en.getKey()),rowId);
		}
		Savor.write(maxId, String.format("%s/maxId",path));
		mask.saveRow(String.format("%s/mask",path),rowId);
	}
	@Override
	public void load(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
			return;
		}
		for(Entry<String,IColumn> en:name2column.entrySet()) {
			log.debug(String.format("to load: %s/%s", path,en.getKey()));
			en.getValue().load(String.format("%s/%s", path,en.getKey()));
		} 
		AtomicInteger maxId=Savor.read( String.format("%s/maxId",path));
		for(Entry<String,IColumn> en:name2column.entrySet()) {
			en.getValue().flush(maxId.get());
		}
		mask.load(String.format("%s/mask",path));
		this.maxId.set(maxId.get());
	}
	@Override
	public void init(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		File init_lock = new File(String.format("%s/init_lock", path));
		if(init_lock.exists()) {
			return;
		}
		for(Entry<String,IColumn> en:name2column.entrySet()) {
			p = new File(String.format("%s/%s", path,en.getKey()));
			p.mkdirs();
			en.getValue().init(String.format("%s/%s", path,en.getKey()));
		}
		mask.init(String.format("%s/mask",path));
		Savor.write(maxId, String.format("%s/maxId",path));
		init_lock.createNewFile();
	}
	@Override
	public byte getType(String fn) {
		IColumn column = name2column.get(fn.toLowerCase());
		return column.getType();
	}
	@Override
	public List<ISortElement> getSortE(String fn,boolean isDesc,String key){
		IColumn column = name2column.get(fn.toLowerCase());
		return column.getSortE(isDesc, key);
	}
	
	public static void main(String[] args) throws Exception {
		IBitIndex mask = new BitIndex();
		AtomicInteger maxId=Savor.read( String.format("%s/maxId","c:/port/xuanYue"));
		System.out.println(maxId);
		mask.flush(maxId.get());
		mask.setAll(true);
		System.out.println(mask.cardinality());
		mask.save(String.format("%s/mask","c:/port/xuanYue"));
	}
	
	
	
}
