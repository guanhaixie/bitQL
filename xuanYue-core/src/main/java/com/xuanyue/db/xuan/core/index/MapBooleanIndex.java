package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.ISortElement;
import com.xuanyue.db.xuan.core.table.sort.SortElement;
import com.xuanyue.db.xuan.core.tools.Savor;
/**
 * bit向量索引的映射。
 * 要求map的key是小数量的，一个key就对应一个bit向量。
 * name2BitIndex 主键对应bit向量索引。
 * name2Id 主键 名称可以各式各样，数据存储的文件名称需要用id,规定id符合文件系统名称规范
 * 
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class MapBooleanIndex implements IColumn<Map<String,Boolean>>{

	private Map<String,BitIndex> name2BitIndex = new HashMap<>();
	private BitIndex mask = new BitIndex();
	private Map<String,String> name2Id = new HashMap<>();//名称可以各式各样，数据存储的文件名称需要用id,因为可以规定id符合文件系统名称规范
	public MapBooleanIndex(Map<String,String> name2Id) {
		this.name2Id.putAll(name2Id);
		name2Id.keySet().forEach(k->{
			name2BitIndex.put(k, new BitIndex());
		});
	}
	@Override
	public void flush(int maxId) {
		name2BitIndex.forEach( (k,v)->{
			v.flush(maxId);
		});
		mask.flush(maxId);
	}
	@Override
	public void save(String path) throws Exception {
		for(Entry<String,BitIndex> kv:name2BitIndex.entrySet()) {
			kv.getValue().save(String.format("%s/name2BitIndex/%s", path,name2Id.get(kv.getKey())));
		}
		Savor.write(name2Id, String.format("%s/name2Id",path));
		mask.save(String.format("%s/mask",path));
	}

	@Override
	public void load(String path) throws Exception {
		name2Id = Savor.read( String.format("%s/name2Id",path));
		if(name2Id==null) {
			name2Id = new HashMap<>();
		}
		BitIndex gz = null;
		for(Entry<String,String> kv:name2Id.entrySet()) {
			gz = new BitIndex();
			gz.load(String.format("%s/name2BitIndex/%s", path,kv.getValue()));
			name2BitIndex.put( kv.getKey()  , gz);
		}
		mask.load(String.format("%s/mask",path));
	}
	@Override
	public void init(String path) throws Exception{
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		Savor.write(name2Id, String.format("%s/name2Id",path));
		mask.init(String.format("%s/mask",path));
		BitIndex gz = null;
		for(Entry<String,String> kv:name2Id.entrySet()) {
			gz = new BitIndex();
			gz.init(String.format("%s/name2BitIndex/%s", path,kv.getValue()));
		}
	}
	public void equealsKey(IBitIndex cache, IBitIndex now, Object value) {
		if(value==null) {
			cache.copyFrom(mask);
			cache.not();
			return;
		}
		String key = value.toString();
		if(!name2BitIndex.containsKey(key)) {
			throw new IndexException(key+"  is not support now!!");
		}
		cache.copyFrom(name2BitIndex.get(key));
		cache.and(mask);
	}
	@Override
	public void equeals(IBitIndex cache, IBitIndex now, Number value) {
		throw new IndexException( this.getClass().getName()+ ".greater  is not support now!!");
	}

	@Override
	public void greater(IBitIndex cache, IBitIndex now, Number value) {
		throw new IndexException( this.getClass().getName()+ ".greater  is not support now!!");
	}

	@Override
	public void less(IBitIndex cache, IBitIndex now, Number value) {
		throw new IndexException( this.getClass().getName()+ ".less  is not support now!!");
	}

	@Override
	public void greaterAndEq(IBitIndex cache, IBitIndex now, Number value) {
		throw new IndexException( this.getClass().getName()+ ".greaterAndEq  is not support now!!");
	}

	@Override
	public void lessAndEq(IBitIndex cache, IBitIndex now, Number value) {
		throw new IndexException( this.getClass().getName()+ ".lessAndEq  is not support now!!");
	}

	@Override
	public void set(int rowId, Object value) {
		if(value==null) {
			mask.set(rowId,false);
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String,Boolean> vs = (Map<String, Boolean>) value;
		name2BitIndex.forEach( (k,v)->{
			Boolean gzv = vs.get(k);
			if(gzv==null) {
				gzv = false;
			}
			v.set(rowId, gzv);
		});
		mask.set(rowId);
	}

	@Override
	public Map<String,Boolean> get(int rowId) {
		if(!mask.get(rowId)) {
			return null;
		}
		Map<String,Boolean> r = new HashMap<String,Boolean>();
		name2BitIndex.forEach( (k,v)->{
			r.put(k, v.get(rowId));
		});
		return r;
	}

	@Override
	public void expr(String method, Object value, List<IBitIndex> caches) {
		if("!=".equals(method)) {
			this.equealsKey(caches.get(0),null, value);
			caches.get(0).not();
		}else if("=".equals(method)) {
			this.equealsKey(caches.get(0),null, value);
		}else {
			throw new IndexException(  this.getClass().getName()+ " not support " + method);
		}
	}

	public static void main(String[] args) {
		Map<String,String> suzi2Id = new HashMap<>();
		suzi2Id.put("sz0", "0");
		suzi2Id.put("sz1", "1");
		suzi2Id.put("sz2", "2");
		suzi2Id.put("sz3", "3");
		suzi2Id.put("sz4", "4");
		suzi2Id.put("sz5", "5");
		suzi2Id.put("sz6", "6");
		suzi2Id.put("sz7", "7");
		suzi2Id.put("sz8", "8");
		suzi2Id.put("sz9", "9");
		MapBooleanIndex te = new MapBooleanIndex(suzi2Id);
		
		Map<String,Boolean> da = new HashMap<>();
		da.put("sz0", true);
		da.put("sz1", true);
		da.put("sz2", true);
		
		te.set(1, da);
		
		te.flush(1);
		System.out.println(te.get(1));
	}
	@Override
	public int getDataSize() {
		return name2BitIndex.size()+1;
	}
	@Override
	public List<ISortElement> getSortE(boolean isDesc, String... names) {
		List<ISortElement> el = new ArrayList<>();
		if(names!=null) {
			for(String name:names) {
				SortElement e = new SortElement();
				e.setData(  name2BitIndex.get(name));
				e.setDesc(isDesc);
				el.add(e);
			}
		}
		return el;
	}
	@Override
	public byte getType() {
		return 5;
	}
	@Override
	public void saveRow(String path, int rowId) throws Exception {
		for(Entry<String,BitIndex> kv:name2BitIndex.entrySet()) {
			kv.getValue().saveRow(String.format("%s/name2BitIndex/%s", path,name2Id.get(kv.getKey())),rowId);
		}
		mask.saveRow(String.format("%s/mask",path),rowId);
		
	}
}
