package com.xuanyue.db.xuan.antlr.impl;

import java.util.List;
import java.util.Map;
/**
 * 查询结果
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年10月16日
 *
 */
public class QueryResult {

	private int count;
	private byte[] types;//列数据类型
	private List<String> fl;//列名称
	private List<Map<Integer,Object>> result;// key:列名称的在fl中的索引
	private long runTimeLong;
	
	public long getRunTimeLong() {
		return runTimeLong;
	}
	public void setRunTimeLong(long runTimeLong) {
		this.runTimeLong = runTimeLong;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public byte[] getTypes() {
		return types;
	}
	public void setTypes(byte[] types) {
		this.types = types;
	}
	public List<String> getFl() {
		return fl;
	}
	public void setFl(List<String> fl) {
		this.fl = fl;
	}
	public List<Map<Integer, Object>> getResult() {
		return result;
	}
	public void setResult(List<Map<Integer, Object>> result) {
		this.result = result;
	}
	
	
	
}
