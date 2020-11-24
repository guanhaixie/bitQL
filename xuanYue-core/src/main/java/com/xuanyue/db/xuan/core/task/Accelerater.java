package com.xuanyue.db.xuan.core.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * bit向量计算 加速器
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年11月21日
 *
 */
public class Accelerater {

	private X2YEXTThreadPoolExecutor exe;
	private BlockingQueue<ITaskSplit> queue = new ArrayBlockingQueue<>(100);
//	private List<ITaskSplit> tasks = new ArrayList<>();
	
	public Accelerater(X2YEXTThreadPoolExecutor exe) {
		this.exe =exe;
	}
	public void prepare(int num) throws InterruptedException {
		CountDownLatch channels = new CountDownLatch(1);
		List<TaskChannel> cl = new ArrayList<>();
		TaskChannel channel = null;
		for(int i=0;i<num;i++) {
			channel = new TaskChannel(queue, channels);
			exe.execute(channel);
			cl.add(channel);
		}
		channels.await();
		System.out.println("prepared ");
		System.out.println("!!!!!!prepared ！！！！！！！！");
	}
	public void free() throws InterruptedException{
		queue.put(TaskSplitImpl.getEndPoint());
	}
	public void put(ITaskSplit split) throws InterruptedException {
		queue.put(split);
//		tasks.add(split);
	}
	
}
