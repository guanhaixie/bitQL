package com.xuanyue.db.xuan.core.index;

import com.xuanyue.db.xuan.msg.VLAUETYPE;

/**
 * 
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 * @version 0.1
 */
public class LongIndex extends NumberIndex{
	@Override
	public VLAUETYPE getType() {
		return VLAUETYPE.LONG;
	}
}
