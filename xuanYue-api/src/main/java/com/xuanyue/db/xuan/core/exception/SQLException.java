package com.xuanyue.db.xuan.core.exception;

/**
 * 索引异常
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
public class SQLException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SQLException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SQLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SQLException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SQLException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SQLException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	
}
