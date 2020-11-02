package com.xuanyue.db.xuan.core.index.accelerate;

public enum BitSetMethod {
	andByCardinality(1),and(2),andNot(3),orByinnerAndNot(4),orByinnerAnd(5),copyFrom(6),
	cardinality(7),andNotByCardinality(8),or(9),orNot(10),not(11),xor(12);
	private int method;
	private BitSetMethod(int method) {
		this.method = method;
	}
	public boolean eq(BitSetMethod tar) {
		return this.method == tar.method;
	}
}
