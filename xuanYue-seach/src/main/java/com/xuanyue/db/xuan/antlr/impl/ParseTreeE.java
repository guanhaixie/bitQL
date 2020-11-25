package com.xuanyue.db.xuan.antlr.impl;

import org.antlr.v4.runtime.tree.ParseTree;

public class ParseTreeE {

	private ParseTree tree;
	private int parallel;
	private int maxSource;
	
	
	public ParseTree getTree() {
		return tree;
	}
	public int getParallel() {
		return parallel;
	}
	
	
	public int getMaxSource() {
		return maxSource;
	}
	public void setMaxSource(int maxSource) {
		this.maxSource = maxSource;
	}
	public void setTree(ParseTree tree) {
		this.tree = tree;
	}
	public void setParallel(int parallel) {
		this.parallel = parallel;
	}
	
	
}
