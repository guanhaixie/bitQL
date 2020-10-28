package com.xuanyue.db.xuan.core.index;

import java.util.Iterator;

import com.xuanyue.db.xuan.core.table.IBitIndex;
/**
 * bit向量 迭代器
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年10月27日
 *
 */
public class BitIndexIterator implements Iterator<Integer> {

	private IBitIndex bitIndex;
	private int now = -1;
	
	public BitIndexIterator(IBitIndex bitIndex) {
		this.bitIndex = bitIndex;
	}
	
	
	@Override
	public boolean hasNext() {
		now = bitIndex.nextSetBit(now+1);
		return now >= 0;
	}

	@Override
	public Integer next() {
		return now;
	}

}
