package com.xuanyue.db.xuan.antlr.impl;

import org.antlr.v4.runtime.tree.ParseTree;

public class ParseTreeE {

	private ParseTree tree;
	private int parallel;
	public ParseTree getTree() {
		return tree;
	}
	public int getParallel() {
		return parallel;
	}
	
	public void setTree(ParseTree tree) {
		this.tree = tree;
	}
	public void setParallel(int parallel) {
		this.parallel = parallel;
	}
	
	
}
