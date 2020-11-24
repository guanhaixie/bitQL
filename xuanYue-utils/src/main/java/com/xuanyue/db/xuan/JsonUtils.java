package com.xuanyue.db.xuan;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();
	static {
		
	}
	
	public static <T>T getObject(String json,Class<T> cla) throws JsonParseException, JsonMappingException, IOException{
		return objectMapper.readValue(json, cla);
	}
	public static String toJson(Object obj) throws JsonProcessingException {
		return objectMapper.writeValueAsString(obj);
	}
}
