package com.xuanyue.db.xuan.msg;

public enum VLAUETYPE{
	INT(0),FLOAT(1),LONG(2),DATE(3),BOOLEAN(4),MAPBOOLEAN(5),MAPNUM(6),LISTNUM(7),STRING(8),MAP1STR2VALUE(9);
	private int type;
	private VLAUETYPE(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public static VLAUETYPE getByType(int type) {
		for(VLAUETYPE v:values()) {
			if(v.type==type) {
				return v;
			}
		}
		return null;
	}
}