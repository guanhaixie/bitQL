package com.xuanyue.db.xuan.core.tools;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> extends LinkedHashMap<K, V> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int MAX_ENTRIES;

    public LruCache(int maxEntries) {
        super(maxEntries + 1, 1.0f, true);
        MAX_ENTRIES = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_ENTRIES;
    }
    
    
    public static void main(String[] args) {
    	Map<String,String> t = Collections.synchronizedMap(new LruCache<>(3));
    	t.put( "1" , "1");
    	t.put( "2" , "2");
    	t.put( "3" , "3");
    	System.out.println(t);
    	t.put( "4" , "4");
    	System.out.println(t);
    	
	}
    
  }
