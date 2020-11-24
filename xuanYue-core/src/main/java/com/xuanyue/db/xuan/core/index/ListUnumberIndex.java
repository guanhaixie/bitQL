package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.ISortElement;
import com.xuanyue.db.xuan.core.table.IXyTable;
import com.xuanyue.db.xuan.core.table.XyTable;
import com.xuanyue.db.xuan.msg.VLAUETYPE;
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
public final class ListUnumberIndex implements IColumn{

	
	private List<UNumberIndex> data;
	private IBitIndex overflowMask = new BitIndex();
	private IXyTable overflowTable;
	
	public ListUnumberIndex(int maxNum,int size) {
		data = new ArrayList<>();
		for(int i=0;i<maxNum;i++) {
			data.add(new UNumberIndex(size));
		}
		overflowTable = new XyTable("overflowTable");
		overflowTable.addColumn("overflowIndex", new UNumberIndex(32));
		overflowTable.addColumn("overflowValue", new UNumberIndex(size));
	}
	
	@Override
	public void save(String path) throws Exception {
		for(int i=0;i<data.size();i++) {
			data.get(i).save(String.format("%s/%s", path,i));
		}
		overflowTable.save(String.format("%s/overflowTable", path));
		overflowMask.save(String.format("%s/overflowMask", path));
	}
	@Override
	public void toBatchLoadMode(String path) {
		for(int i=0;i<data.size();i++) {
			data.get(i).toBatchLoadMode(String.format("%s/%s", path,i));
		}
		overflowTable.toBatchLoadMode(String.format("%s/overflowTable", path));
		overflowMask = new BatchBitIndex(String.format("%s/overflowMask", path));
	}
	@Override
	public void load(String path) throws Exception {
		for(int i=0;i<data.size();i++) {
			data.get(i).load(String.format("%s/%s", path,i));
		}	
		overflowTable.load(String.format("%s/overflowTable", path));
		overflowMask.load(String.format("%s/overflowMask", path));
	}
	@Override
	public void init(String path) throws Exception{
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		for(int i=0;i<data.size();i++) {
			data.get(i).init(String.format("%s/%s", path,i));
		}
		overflowTable.init(String.format("%s/overflowTable", path));
		overflowMask.init(String.format("%s/overflowMask", path));
	}
	@Override
	public void equeals(IBitIndex cache, IBitIndex now, Number value) {
		cache.setAll(false);
		for(int i=0;i<data.size();i++) {
			data.get(i).equeals(now,null, value);
			cache.or(now);
		}
		List<IBitIndex> ofBis = null;
		try {
			ofBis = overflowTable.apply(1);
			overflowTable.expr("overflowValue", "=", value, ofBis);
			
			UNumberIndex c = (UNumberIndex)overflowTable.getName2column().get("overflowIndex");
			IBitIndex bs = ofBis.get(0);
			for (int j = bs.nextSetBit(0); j >= 0; j = bs.nextSetBit(j+1)) {
				cache.set(c.get(j).intValue());
			}
		} catch(Exception e){
			throw new IndexException("error at overflowValue match");
		}finally {
			overflowTable.returnSource(ofBis);
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
			@SuppressWarnings("unchecked")
			List<Long> ts = (List<Long>)value;
			if(ts.size()>data.size()) {
				throw new IndexException("value is too big , value size is "+ts.size()+"  data size is "+data.size());
			}
			int i=0;
			for(;i<ts.size();i++) {
				data.get(i).set(rowId, ts.get(i));
			}
			for(;i<data.size();i++) {
				data.get(i).set(rowId,null);
			}
		}
		
	}

	@Override
	public List<Long> get(int rowId) {
		List<Long> r = new ArrayList<Long>();
		data.forEach(e->{
			Long v = e.get(rowId);
			if(v!=null) {
				r.add(v);
			}
		});
		return r;
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
		IBitIndex r = caches.get(0);
		r.setAll(false);
		if("contains".equals(method.toLowerCase())) {
			for(UNumberIndex e:data) {
				e.equeals(caches.get(1), null, value);
				r.or(caches.get(1));
			}
		}else {
			throw new IndexException(" not support the method "+method);		
		}
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
		throw new IndexException(" not support the method getSortE");
	}
	@Override
	public boolean checkSortE(boolean isDesc,String names) {
		return false;
	}
	@Override
	public VLAUETYPE getType() {
		return VLAUETYPE.LISTNUM;
	}

	@Override
	public void saveRow(String path, int rowId) throws Exception {
		for(int i=0;i<data.size();i++) {
			data.get(i).saveRow(String.format("%s/%s", path,i),rowId);
		}
	}

}
