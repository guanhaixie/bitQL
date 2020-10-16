package com.xuanyue.db.xuan.core.exception;

/**
 * 银行家算法异常
 * @author 解观海
 * @email  guanhaixie@sina.cn
 * @date 2020年6月23日
 *
 */
public class BankerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankerException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BankerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BankerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BankerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BankerException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
