package com.xuanyue.db.xuan.core.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
/**
 * bit向量的计算需要在多线程加速时，计算任务被拆分成多个向量计算子任务。
 * 每个TaskChannel本质是一个线程，循环从子任务队列取出执行。
 * 
 * TaskChannel 对象被线程池调度执行后，占用一个线程后
 * 从子任务队列queue中取出任务执行，取出的子任务如果是结束标记则退出执行，
 * 如果是计算算子任务，则执行后再取出任务判断执行
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年11月21日
 *
 */
public class TaskChannel implements Runnable{

	/**计算时，最少需要已占有的通道数*/
	private CountDownLatch minChannelNumber;
	/**当前通道获得一个线程资源后，调用countDown方法    */
	private CountDownLatch channelLatch = new CountDownLatch(1);
	/**子任务队列*/
	private BlockingQueue<ITaskSplit> queue;
	
	/**
	 * 
	 * @param queue 子任务队列
	 * @param minChannelNumber 最小占用通道数
	 */
	public TaskChannel(BlockingQueue<ITaskSplit> queue,CountDownLatch minChannelNumber) {
		this.queue = queue;
		this.minChannelNumber = minChannelNumber;
	}
	
	/**等待当前通道获得一个线程资源*/
	public void waitForChannelReady() throws InterruptedException{
		channelLatch.await();
	}
	
	@Override
	public void run() {
		channelLatch.countDown();//通道已经开通:即 计算任务已经占用当前线程
		minChannelNumber.countDown();
		try {
			boolean toRun = true;
			while(toRun) {
				ITaskSplit split = queue.take();
				if(split.isEndPoint()) {
					toRun = false;
					split.endRunned(queue);
				}else {
					split.biz();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}