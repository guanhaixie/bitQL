package com.xuanyue.db.xuan.core.db;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.xuanyue.db.xuan.core.table.IXyTable;
import com.xuanyue.db.xuan.core.table.XyTable;
/**
 * 库
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年10月16日
 *
 */
public class XyDB implements IXyDB{

	private String name;
	public String dataPath;
	private Map<String,IXyTable> name2table = new HashMap<>();
	
	private void addTable(TableMeta tm) throws Exception {
		IXyTable table = new XyTable(tm.getSource(),tm.getName());
		Map<String,ColumnMeta> columns = tm.getColumns();
		for(Entry<String,ColumnMeta> en:columns.entrySet()) {
			table.addColumn(en.getKey(),  en.getValue().column()  );
		}
		table.init(String.format("%s/%s/%s", dataPath,name,tm.getName()) );
		name2table.put(tm.getName(), table);
	}
	@Override
	public IXyTable getTable(String name) {
		return name2table.get(name);
	}

	@Override
	public void init(DBMeta meta) throws Exception{
		name = meta.getName();
		dataPath=meta.getDataPath();
		File dp = new File(dataPath);
		dp.mkdirs();
		for(TableMeta tm:meta.getTables().values()) {
			addTable(tm);
		}
		
	}
	@Override
	public void load() throws Exception {
		for(Entry<String,IXyTable> en:name2table.entrySet()) {
			en.getValue().load( String.format("%s/%s/%s", dataPath,name, en.getKey() ) );
		}
		
	}

}
