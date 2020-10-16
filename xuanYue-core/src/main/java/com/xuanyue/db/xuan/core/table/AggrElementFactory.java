package com.xuanyue.db.xuan.core.table;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.xuanyue.db.xuan.core.table.groupby.AggrElement;
import com.xuanyue.db.xuan.core.table.groupby.IAggrElement;

public class AggrElementFactory  extends BasePooledObjectFactory<IAggrElement>{
	
	private int type;
	public AggrElementFactory(int type) {
		this.type = type;
	}
	@Override
	public IAggrElement create() throws Exception {
		AggrElement r =  new AggrElement(type);
		return r;
	}

	@Override
	public PooledObject<IAggrElement> wrap(IAggrElement set) {
		return new DefaultPooledObject<>(set);
	}

}
