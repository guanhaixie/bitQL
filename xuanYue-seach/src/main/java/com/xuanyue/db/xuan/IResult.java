package com.xuanyue.db.xuan;

import java.util.List;
import java.util.Map;

import com.xuanyue.db.xuan.msg.VLAUETYPE;
import com.xuanyue.db.xuan.msg.X2YValue;

public interface IResult {

	public int count();
	public List<Map<String,X2YValue>> getData();
	public Map<String,VLAUETYPE> getTypes();
}
