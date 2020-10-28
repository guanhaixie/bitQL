package com.xuanyue.db.xuan.core.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
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
}
