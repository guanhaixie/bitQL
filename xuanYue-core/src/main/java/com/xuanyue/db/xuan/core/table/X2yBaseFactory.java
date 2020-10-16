package com.xuanyue.db.xuan.core.table;


import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.xuanyue.db.xuan.core.index.BitIndex;

public class X2yBaseFactory<T> extends BasePooledObjectFactory<T>{
	@Override
	public T create() throws Exception {
		@SuppressWarnings("unchecked")
		T r =  (T)new BitIndex();
		return r;
	}
	@Override
	public PooledObject<T> wrap(T set) {
		return new DefaultPooledObject<>(set);
	}
}
