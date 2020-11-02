package com.xuanyue.db.xuan.core.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class X2yThreadPoolExecutor extends ThreadPoolExecutor{
	
	public X2yThreadPoolExecutor() {
		super(20, 40, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new RejectedExecutionHandler() {
			/**
			 * 当出现任务风暴时，防止任务丢失
			 */
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				r.run();
			}
		});
	}
	public X2yThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,TimeUnit unit,int queueSize) {
		
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new ArrayBlockingQueue<Runnable>(queueSize), new RejectedExecutionHandler() {
			/**
			 * 当出现任务风暴时，防止任务丢失
			 */
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				r.run();
			}
		});
	}
	public X2yThreadPoolExecutor(int corePoolSize, long keepAliveTime,TimeUnit unit,int queueSize) {
		
		super(corePoolSize, corePoolSize, keepAliveTime, unit, new PriorityBlockingQueue<Runnable>(queueSize), new RejectedExecutionHandler() {
			/**
			 * 当出现任务风暴时，防止任务丢失
			 */
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				r.run();
			}
		});
	}
	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		super.execute(command);
	}
	public <T>Future<T> submitPriorityTask(PriorityFutureTask<T> ftask){
		execute(ftask);
		return ftask;
	}
	
	
	
}
