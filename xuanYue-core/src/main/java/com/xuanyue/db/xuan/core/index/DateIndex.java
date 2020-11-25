package com.xuanyue.db.xuan.core.index;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IColumn;
import com.xuanyue.db.xuan.core.table.ISortElement;
import com.xuanyue.db.xuan.msg.VLAUETYPE;
/**
 * 最小精度为秒
 * 用制定的长度存放。
 * size：表示字段长度
 * [1970-01-01 00:00:00]减 2^size+1秒    到  [1970-01-01 00:00:00]加 2^size-+1秒   之间的时间数据。
 * 默认用34位有符号整形存放，     即可以正确处理               [1970-01-01 00:00:00]减 2^24+1秒    到  [1970-01-01 00:00:00]加 2^24-+1秒   之间的时间数据。
 * （大约是1970年上下浮动544年          [1426,2514] ）
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class DateIndex implements IColumn{
	
	private NumberIndex data = new NumberIndex(35);
	public DateIndex() {
		this(35);
	}
	public DateIndex(int size) {
		data = new NumberIndex(size);
	}
	public IBitIndex getMask() {
		return data.getMask();
	}
	@Override
	public void flush(int maxId) {
		data.flush(maxId);
	}
	@Override
	public void equeals(IBitIndex cache, IBitIndex now, Number valueO) {
		if(valueO==null) {
			cache.copyFrom(getMask());
			cache.not();
			return;
		}
		data.equeals(cache,null, valueO);
	}

	@Override
	public void greater(IBitIndex cache, IBitIndex now, Number valueO) {
		data.greater(cache, now, valueO);
	}

	@Override
	public void less(IBitIndex cache, IBitIndex now,Number valueO) {
		data.less(cache, now, valueO);
	}

	@Override
	public void greaterAndEq(IBitIndex cache, IBitIndex now, Number valueO) {
		data.greaterAndEq(cache, now, valueO);
	}

	@Override
	public void lessAndEq(IBitIndex cache, IBitIndex now, Number valueO) {
		data.lessAndEq(cache, now, valueO);
	}

	@Override
	public void set(int rowId, Object valueO) {
		if(valueO==null) {
			data.set(rowId, valueO);
			return;
		}
			
		Date value=(Date)valueO;
		data.set(rowId, value.getTime()/1000);
	}

	@Override
	public Date get(int rowId) {
		Long ms = data.get(rowId);
		if(ms==null) {
			return null;
		}
		return new Date(ms*1000);
	}

	@Override
	public void expr(String method, Object value,List<IBitIndex> caches) {
		Date da = (Date)value;
		long v = da.getTime()/1000;
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
			throw new IndexException("DateIndex not support " + method);
		}
	}

	public int checkExpr(String method,Object value) {
		if(!(value instanceof Date)) {
			return 0;
		}
		if("!=".equals(method)) {
			return 1;
		}else if("=".equals(method)) {
			return 1;
		}else if(">=".equals(method)){
			return 2;
		}else if(">".equals(method)){
			return 2;
		}else if("<=".equals(method)){
			return 2;
		}else if("<".equals(method)){
			return 2;
		}else {
			return 0;
		}
		
	}
	
	@Override
	public void save(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		data.save(String.format("%s/data", path));
	}
	@Override
	public void toBatchLoadMode(String path) {
		data.toBatchLoadMode(String.format("%s/data", path));
	}
	@Override
	public void load(String path) throws Exception {
		File p = new File(path);
		if(!p.exists()) {
			throw new IndexException(String.format("%s is not exists", path));
		}
		data.load(String.format("%s/data", path));
	}
	@Override
	public void init(String path) throws Exception{
		File p = new File(path);
		if(!p.exists()) {
			p.mkdirs();
		}
		data.init(String.format("%s/data", path));
	}
	@Override
	public int getDataSize() {
		return data.getDataSize();
	}
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date d = new Date();
		System.out.println(formatter.format(d));
		System.out.println(formatter.parse("1970-01-01 08:00:00").getTime());
		
		System.out.println(d.getTime()/1000);
	}
	@Override
	public List<ISortElement> getSortE(boolean isDesc, String... names) {
		return data.getSortE(isDesc, names);
	}
	@Override
	public VLAUETYPE getType() {
		return VLAUETYPE.DATE;
	}
	@Override
	public void saveRow(String path, int rowId) throws Exception {
		data.saveRow(String.format("%s/data", path), rowId);
	}
	@Override
	public boolean checkSortE(boolean isDesc,String names) {
		return true&&names==null;
	}
}
