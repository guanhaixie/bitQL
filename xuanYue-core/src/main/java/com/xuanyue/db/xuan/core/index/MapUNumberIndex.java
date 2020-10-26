package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.ISortElement;
import com.xuanyue.db.xuan.core.table.sort.SortElement;
import com.xuanyue.db.xuan.core.tools.Savor;
/**
 * 类似 MapbooleanIndex ，只是数据标出了无符号数值。
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class MapUNumberIndex implements IColumn<Map<String,Long>>{

	private Map<String,UNumberIndex> name2BitIndex = new HashMap<>();
	private IBitIndex mask = new BitIndex();
	private Map<String,String> name2Id = new HashMap<>();
	private int size=20;
	public MapUNumberIndex(int size,Map<String,String> name2Id) {
		this.size = size;
		this.name2Id.putAll(name2Id);
	}
	@Override
	public void flush(int maxId) {
		name2BitIndex.forEach( (k,v)->{
			v.flush(maxId);
		} );
		mask.flush(maxId);
	}
	@Override
	public void save(String path) throws Exception {
		for(Entry<String,UNumberIndex> kv:name2BitIndex.entrySet()) {
			kv.getValue().save(String.format("%s/name2BitIndex/%s", path,name2Id.get(kv.getKey())));
		}
		Savor.write(size, String.format("%s/size",path));
		Savor.write(name2Id, String.format("%s/name2Id",path));
		mask.save(String.format("%s/mask",path));
	}

	@Override
	public void toBatchLoadMode(String path) {
		for(Entry<String,UNumberIndex> kv:name2BitIndex.entrySet()) {
			kv.getValue().toBatchLoadMode(String.format("%s/name2BitIndex/%s", path,name2Id.get(kv.getKey())));
		}
		mask = new BatchBitIndex(String.format("%s/mask", path));
	}
	
	@Override
	public void load(String path) throws Exception {
		size = Savor.read( String.format("%s/size",path));
		name2Id = Savor.read( String.format("%s/name2Id",path));
		if(name2Id==null) {
			name2Id = new HashMap<>();
		}
		UNumberIndex gz = null;
		for(Entry<String,String> kv:name2Id.entrySet()) {
			gz = new UNumberIndex();
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
		Savor.write(size, String.format("%s/size",path));
		Savor.write(name2Id, String.format("%s/name2Id",path));
		mask.init(String.format("%s/mask",path));
		
		UNumberIndex gz = null;
		for(Entry<String,String> kv:name2Id.entrySet()) {
			gz = new UNumberIndex();
			gz.init(String.format("%s/name2BitIndex/%s", path,kv.getValue()));
		}
	}

	@Override
	public void equeals(IBitIndex cache, IBitIndex now, Number value) {
	}

	@Override
	public void greater(IBitIndex cache, IBitIndex now, Number value) {
	}

	@Override
	public void less(IBitIndex cache, IBitIndex now, Number value) {
	}

	@Override
	public void greaterAndEq(IBitIndex cache, IBitIndex now, Number value) {
	}

	@Override
	public void lessAndEq(IBitIndex cache, IBitIndex now, Number value) {
	}

	@Override
	public void set(int rowId, Object value) {
	}

	@Override
	public Map<String, Long> get(int rowId) {
		return null;
	}

	@Override
	public void expr(String method, Object value, List<IBitIndex> caches) {
		
	}
	@Override
	public int getDataSize() {
		return name2BitIndex.size()*size+1;
	}
	@Override
	public List<ISortElement> getSortE(boolean isDesc, String... names) {
		List<ISortElement> el = new ArrayList<>();
		if(names!=null) {
			for(String name:names) {
				el.addAll( name2BitIndex.get(name).getSortE(isDesc));
			}
		}
		return el;
	}
	@Override
	public byte getType() {
		return 6;
	}
	@Override
	public void saveRow(String path, int rowId) throws Exception {
		for(Entry<String,UNumberIndex> kv:name2BitIndex.entrySet()) {
			kv.getValue().saveRow(String.format("%s/name2BitIndex/%s", path,name2Id.get(kv.getKey())),rowId);
		}
		mask.saveRow(String.format("%s/mask",path),rowId);
		
	}
}
