package com.xuanyue.db.xuan.core.db;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xuanyue.db.xuan.core.table.IColumn;
/**
 * 列信息
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ColumnMeta{

	private String name;
	private String className;//Class<? extends IColumn<?>>
	private List<Integer> parameters;
	private Map<String,String> name2Id;
	
	@JsonIgnore
	public String getAllPatameters() {
		if(parameters==null) {
			return "";
		}else {
			StringBuffer r = new StringBuffer();
			for(int i=0;i<parameters.size();i++) {
				r.append(parameters.get(i));
				if(i<parameters.size()-1) {
					r.append(",");
				}
			}
			return r.toString();
		}
	}
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
		Class<? extends IColumn> cla = (Class<? extends IColumn>) Class.forName(
				String.format("com.xuanyue.db.xuan.core.index.%s", className)
				);
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
