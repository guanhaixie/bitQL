package com.xuanyue.db.xuan;

import com.xuanyue.db.xuan.core.index.BitIndex;

public class T {

	public static void main(String[] args) throws Exception {
		BitIndex b = new BitIndex();
//		b.load("E:/data/xiegh/t_ph/mask");
//		System.out.println(b.get(100000000));
		b.flush(100000000);
		b.setAll(true);
		b.save("E:/data/xiegh/t_ph/mask");
		System.out.println(b.cardinality());
		System.out.println(b.nextSetBit(0));
	}

}
