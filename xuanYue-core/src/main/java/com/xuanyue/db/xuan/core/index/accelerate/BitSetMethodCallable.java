package com.xuanyue.db.xuan.core.index.accelerate;

import java.util.concurrent.Callable;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.table.IXGHBitSet;

/**
 * 子bit向量 计算器
 * 
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年11月1日
 *
 */
public class BitSetMethodCallable implements Callable<Integer> {

	private IXGHBitSet me;
	private IXGHBitSet set;
	private IXGHBitSet set2;
	
	private BitSetMethod method;
	public BitSetMethodCallable(IXGHBitSet me,IXGHBitSet set,                BitSetMethod method) {
		super();
		this.me = me;
		this.set = set;
		this.method = method;
	}
	public BitSetMethodCallable(IXGHBitSet me,IXGHBitSet set,IXGHBitSet set2,BitSetMethod method) {
		super();
		this.me = me;
		this.set = set;
		this.set2 = set2;
		this.method = method;
	}

	@Override
	public Integer call() throws Exception {
		if(method.eq( BitSetMethod.andByCardinality )) {
			// bitCount (me and set) 不改变 me和set
			return me.andByCardinality(set);
		}else if(method.eq( BitSetMethod.and )) {
			//  me = me and set
			me.and(set);
			return 1;
		}else if(method.eq( BitSetMethod.andNot )) {
			// me = me and (not set)  不改变 set
			me.andNot(set);
			return 1;
		}else if(method.eq( BitSetMethod.orByinnerAndNot )) {
			// me = me or ( set and (not set2)  )  不改变set和set2
			me.orByinnerAndNot(set,set2);
			return 1;
		}else if(method.eq( BitSetMethod.orByinnerAnd )) {
			// me = me or ( set and set2  ) 不改变set和set2
			me.orByinnerAnd(set,set2);
			return 1;
		}else if(method.eq( BitSetMethod.copyFrom )) {
			//me = set 不改变set
			me.copyFrom(set);
			return 1;
		}else if(method.eq( BitSetMethod.cardinality )) {
			return me.cardinality();
		}else if(method.eq( BitSetMethod.andNotByCardinality )) {
			//me = bitCount ( me and (not set) )
			return me.andNotByCardinality(set);
		}else if(method.eq( BitSetMethod.or )) {
			// me = me or set   不改变set
			me.or(set);
			return 1;
		}else if(method.eq( BitSetMethod.orNot )) {
			// me = me or (not set)  不改变set
			me.orNot(set);
			return 1;
		}else if(method.eq( BitSetMethod.not )) {
			me.not();
			return 1;
		}else if(method.eq( BitSetMethod.xor )) {
			//me = me xor set   不改变set
			me.xor(set);
			return 1;
		}else {
			throw new IndexException("method ["+method+"] is not support!");
		}
	}

}
