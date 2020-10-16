package com.xuanyue.db.xuan.core.db;

import java.util.HashMap;
import java.util.Map;

public class TableMeta {

	private Map<String,ColumnMeta> columns = new HashMap<>();
	private String name;
	private int source;
	
	
	public void addColumn(Class<?> cla,String name,int ... ps) throws Exception{
		ColumnMeta cm = new ColumnMeta();
		cm.setClassName(cla.getName());
		cm.setName(name);
		cm.setParameters(ps);
		columns.put(name, cm);
	}
	
	
	public Map<String,ColumnMeta> getColumns() {
		return columns;
	}

	public void setColumns(Map<String,ColumnMeta> columns) {
		this.columns = columns;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
	
	
	
}
