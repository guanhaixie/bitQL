package com.xuanyue.db.xuan.core.table.groupby;

/**
 * 均值函数
 * @author guanh
 *
 */
public class Avg implements IAvg {

	private Sumer_Min_Max sumer;
	private Countor countor;
	
	@Override
	public boolean check(int maxId) {
		return sumer.check(maxId)&&countor.check(maxId);
	}
	@Override
	public void flush(int maxId) {
		sumer.flush(maxId);
		countor.flush(maxId);
	}
	@Override
	public void forEche(int index,Number value) {
		sumer.sum(index, value);
		countor.inc(index);
	}
	
}
