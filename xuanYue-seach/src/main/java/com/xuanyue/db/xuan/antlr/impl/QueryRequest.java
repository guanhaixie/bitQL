package com.xuanyue.db.xuan.antlr.impl;

import java.util.Map;

public class QueryRequest {

	private String sql;
	private Map<Integer,Object> parameters;//传参    ?1
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Map<Integer, Object> getParameters() {
		return parameters;
	}
	public void setParameters(Map<Integer, Object> parameters) {
		this.parameters = parameters;
	}
	
	
	
}
