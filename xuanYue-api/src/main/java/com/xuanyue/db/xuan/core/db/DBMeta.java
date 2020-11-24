package com.xuanyue.db.xuan.core.db;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 库信息
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DBMeta {

	private String name;
	private String dataPath;
	private Map<String,TableMeta> tables = new HashMap<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataPath() {
		return dataPath;
	}
	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
	public Map<String, TableMeta> getTables() {
		return tables;
	}
	public void setTables(Map<String, TableMeta> tables) {
		this.tables = tables;
	}
	public void add(String name, TableMeta table) {
		tables.put(name, table);
	}
	
	
	
}
