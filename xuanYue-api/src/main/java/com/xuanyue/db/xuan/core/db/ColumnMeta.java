package com.xuanyue.db.xuan.core.db;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xuanyue.db.xuan.core.table.IColumn;
/**
 * 列信息
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
public class ColumnMeta{

	private String name;
	private String className;//Class<? extends IColumn<?>>
	private List<Integer> parameters;
	private Map<String,String> name2Id;
	
	public Map<String, String> getName2Id() {
		return name2Id;
	}
	public void setName2Id(Map<String, String> name2Id) {
		this.name2Id = name2Id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<Integer> getParameters() {
		return parameters;
	}
	public void setParameters(int[] parameters) {
		this.parameters = new ArrayList<>();
		for(int i=0;i<parameters.length;i++) {
			this.parameters.add(parameters[i]  );
		}
	}
	
	@SuppressWarnings("unchecked")
	public IColumn column() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<? extends IColumn> cla = (Class<? extends IColumn>) Class.forName(className);
		if(parameters!=null&&parameters.size()>0) {
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[parameters.size()];
			for(int i=0;i<types.length;i++) {
				types[i] = int.class;
			}
			Constructor<? extends IColumn> c = cla.getDeclaredConstructor(types);
			IColumn r = c.newInstance(parameters.toArray());
			return r;
		}else if(name2Id!=null) {
			Constructor<? extends IColumn> c = cla.getDeclaredConstructor(Map.class);
			IColumn r = c.newInstance(name2Id);
			return r;
		}
		return cla.newInstance();
	}
	
	
}
