package com.xuanyue.db.xuan.core.task;

import java.util.concurrent.BlockingQueue;

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
public interface ITaskSplit{

	/**
	 * 是否为结束标记
	 * @return
	 */
	public boolean isEndPoint();
	/**
	 * 计算算子
	 */
	public void biz();
	/**
	 * 结束执行
	 * @param queue
	 */
	public void endRunned(BlockingQueue<ITaskSplit> queue);
	/**
	 * 获得执行结果
	 * @return
	 * @throws InterruptedException
	 */
	public int get() throws InterruptedException;
	
	public void setEndPoint(boolean endPoint);
	
}
