package com.xuanyue.db.xuan.antlr.impl;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class ERRORCheckor extends BaseErrorListener{

	private int errorCount;
	private boolean toPrint=true;
	private String msg;
	public ERRORCheckor(boolean toPrint) {
		this.toPrint = toPrint;
	}
	public String getMsg() {
		return msg;
	}
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
			int charPositionInLine, String msg, RecognitionException e) {
		errorCount++;
		msg = String.format("error at %s, line %s(char %s) ", offendingSymbol,line,charPositionInLine);
		if(toPrint)
			System.out.println(msg);
	}
	
	public boolean isSuccess() {
		return errorCount==0;
	}
}
