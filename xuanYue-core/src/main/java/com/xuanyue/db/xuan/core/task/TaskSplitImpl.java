package com.xuanyue.db.xuan.core.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.index.accelerate.BitSetMethod;
import com.xuanyue.db.xuan.core.table.IXGHBitSet;

/**
 * bit向量 计算子任务
 * 
 * bit向量的计算需要在多线程加速时，计算任务被拆分成多个向量计算子任务。
 * 每个TaskChannel本质是一个线程，循环从子任务队列取出执行。
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年11月21日
 *
 */
public class TaskSplitImpl implements ITaskSplit {

	private CountDownLatch channelLatch = new CountDownLatch(1);
	
	private IXGHBitSet me;
	private IXGHBitSet set;
	private IXGHBitSet set2;
	private BitSetMethod method;
	private int result;
	
	private boolean endPoint=false;
	
	
	
	
	public int get() throws InterruptedException {
		channelLatch.await();
		return result;
	}
	
	private TaskSplitImpl() {
		this.endPoint = true;
	}
	public static ITaskSplit getEndPoint() {
		return new TaskSplitImpl();
	}
	public TaskSplitImpl(IXGHBitSet me,IXGHBitSet set,                BitSetMethod method) {
		this.me = me;
		this.set = set;
		this.method = method;
	}
	public TaskSplitImpl(IXGHBitSet me,IXGHBitSet set,IXGHBitSet set2,BitSetMethod method) {
		this.me = me;
		this.set = set;
		this.set2 = set2;
		this.method = method;
	}
	
	private int call() {
		
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
	
	
	@Override
	public boolean isEndPoint() {
		return endPoint;
	}

	@Override
	public void biz() {
		result=call();
		channelLatch.countDown();
	}

	@Override
	public void endRunned(BlockingQueue<ITaskSplit> queue) {
		queue.add( this );
	}
	@Override
	public void setEndPoint(boolean endPoint) {
		this.endPoint = endPoint;
	}
}
