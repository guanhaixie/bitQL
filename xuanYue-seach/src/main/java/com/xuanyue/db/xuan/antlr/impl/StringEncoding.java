package com.xuanyue.db.xuan.antlr.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.xuanyue.db.xuan.core.table.IStringEncoding;

public class StringEncoding implements IStringEncoding {
	
	
	
	@Override
	public String encoding(Object value) {
		if(value == null) {
			return "null";
		}else if(value instanceof Date ) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.format(value);
		}else {
			return value.toString();
		}
		
	}

}
