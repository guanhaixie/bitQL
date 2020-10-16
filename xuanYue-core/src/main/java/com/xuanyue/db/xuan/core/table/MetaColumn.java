package com.xuanyue.db.xuan.core.table;

public class MetaColumn {
//FLOATIndex
//	BOOLEN(1,"BooleanIndex",1,0),DATE(2,"DateIndex"),BOOLEN(1,"FLOATIndex",1,0);
	private int type;
	private int size;
	private int eNum;
	private String name;
	private MetaColumn(int type,String name) {
		this(type,name,0,0);
	}
	private MetaColumn(int type,String name,int size,int eNum) {
		this.type = type;
		this.name = name;
		this.eNum = eNum;
		this.size = size;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int geteNum() {
		return eNum;
	}
	public void seteNum(int eNum) {
		this.eNum = eNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
