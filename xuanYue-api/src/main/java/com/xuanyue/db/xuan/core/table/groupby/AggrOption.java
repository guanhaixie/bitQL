package com.xuanyue.db.xuan.core.table.groupby;

public enum AggrOption {

	SUM(1),AVG(2),MIN(3),MAX(4),COUNT(5);
	private int op;
	private AggrOption(int op) {
		this.op = op;
	}
	
	public int getOp() {
		return this.op;
	}
	public boolean eq(AggrOption tar) {
		return this.op == tar.op;
	}
}
