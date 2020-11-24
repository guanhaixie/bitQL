package com.xuanyue.db.xuan.core.task;

public interface RejectedExecutionHandler {
	 public void rejectedExecution(Runnable r, X2YEXTThreadPoolExecutor executor);
}
