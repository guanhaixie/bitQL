package com.xuanyue.db.xuan.core.table;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.xuanyue.db.xuan.core.index.BitIndex;

public class BitIndexFactory  extends BasePooledObjectFactory<IBitIndex>{
	
	private AtomicInteger maxId;
	
	@Override
	public IBitIndex create() throws Exception {
		IBitIndex r =  new BitIndex();
		r.flush(maxId.get());
		return r;
	}

	@Override
	public PooledObject<IBitIndex> wrap(IBitIndex set) {
		return new DefaultPooledObject<>(set);
	}

	@Override
	public boolean validateObject(PooledObject<IBitIndex> p) {
		IBitIndex me = p.getObject();
		return me.check(maxId.get());
	}
	public void setMaxId(AtomicInteger maxId) {
		this.maxId = maxId;
	}
}
