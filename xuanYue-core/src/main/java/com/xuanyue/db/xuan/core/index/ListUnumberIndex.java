package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.ISortElement;
/**
 * 无符号整数集合,一个列需要制定最多放N个数值。一个数值有size个bit
 * 这样的数据列需要慎重考虑，因为N的大小，因为列大小为     N*size 
 * 
 * 在后续的版本中会开放有序集合的索引属性，这样就可以按照权重将数据放入集合，可以支持topN的功能。
 * 
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public final class ListUnumberIndex implements IColumn<String>{

	
	private List<UNumberIndex> data;
	
	public ListUnumberIndex(int maxNum,int size) {
		data = new ArrayList<>();
		for(int i=0;i<maxNum;i++) {
			data.add(new UNumberIndex(size));
		}
	}
	
	@Override
	public void save(String path) throws Exception {
		for(int i=0;i<data.size();i++) {
			data.get(i).save(String.format("%s/%s", path,i));
		}
	}

	@Override
	public void load(String path) throws Exception {
		for(int i=0;i<data.size();i++) {
			data.get(i).load(String.format("%s/%s", path,i));
		}		
	}
	@Override
	public void init(String path) throws Exception{
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		for(UNumberIndex d:data) {
			d.init(String.format("%s/data", path));
		}
		
	}
	@Override
	public void equeals(IBitIndex cache, IBitIndex now, Number value) {
		cache.setAll(false);
		for(int i=0;i<data.size();i++) {
			data.get(i).equeals(now,null, value);
			cache.or(now);
		}
	}

	@Override
	public void greater(IBitIndex cache, IBitIndex now, Number value) {
		throw new IndexException(String.format(" not support the method %s",  new Exception().getStackTrace()[0].getMethodName()));
	}

	@Override
	public void less(IBitIndex cache, IBitIndex now, Number value) {
		throw new IndexException(String.format(" not support the method %s",  new Exception().getStackTrace()[0].getMethodName()));		
	}

	@Override
	public void greaterAndEq(IBitIndex cache, IBitIndex now, Number value) {
		throw new IndexException(String.format(" not support the method %s",  new Exception().getStackTrace()[0].getMethodName()));		
	}

	@Override
	public void lessAndEq(IBitIndex cache, IBitIndex now, Number value) {
		throw new IndexException(String.format(" not support the method %s",  new Exception().getStackTrace()[0].getMethodName()));		
	}

	
	@Override
	public void set(int rowId, Object value) {
		if(value==null) {
			data.forEach(e->{
				e.set(rowId, null);
			});
		}else {
			
			
			
			String[] ts = value.toString().split(",");
			int i=0;
			for(;i<ts.length&&i<data.size();i++) {
				if(ts[i].trim().length()>0) {
					data.get(i).set(rowId, Long.parseLong(ts[i].trim()));
				}
			}
			for(;i<data.size();i++) {
				data.get(i).set(rowId,null);
			}
		}
		
	}

	@Override
	public String get(int rowId) {
		StringBuffer r = new StringBuffer();
		data.forEach(e->{
			Long v = e.get(rowId);
			if(v!=null) {
				if(r.length()>0) {
					r.append(",");
				}
				r.append(v);
			}
		});
		if(r.length()==0) {
			return null;
		}
		return r.toString();
	}

	@Override
	public void expr(String method, Object value, List<IBitIndex> caches) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush(int maxId) {
		data.forEach(d->{
			d.flush(maxId);
		});
	}

	@Override
	public int getDataSize() {
		int c =0;
		for(UNumberIndex e:data) {
			c+=e.getDataSize();
		}
		return c;
	}

	@Override
	public List<ISortElement> getSortE(boolean isDesc, String... names) {
		
		return null;
	}

	@Override
	public byte getType() {
		return 7;
	}

	@Override
	public void saveRow(String path, int rowId) throws Exception {
		for(int i=0;i<data.size();i++) {
			data.get(i).saveRow(String.format("%s/%s", path,i),rowId);
		}
	}

}
