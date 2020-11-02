package com.xuanyue.db.xuan.core.task;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
/**
 * 
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年11月2日
 *
 */
public class PriorityFutureTask<T> extends FutureTask<T> implements Comparable<PriorityFutureTask<T>>{

	private long priority;
	public PriorityFutureTask(Callable<T> callable,long priority) {
		super(callable);
		this.priority = priority;
	}
	@Override
	public int compareTo(PriorityFutureTask<T> tar) {
		return this.priority>tar.priority?-1:( this.priority==tar.priority?0:1  );
	}

}
