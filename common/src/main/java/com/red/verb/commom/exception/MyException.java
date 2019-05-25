package com.red.verb.commom.exception;

/**
 * exception
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/24     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-24 10:07
 * @since 1.0.0
 */
public class MyException extends RuntimeException {
	public MyException(String msg) {
		super(msg);
	}

	public MyException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public MyException(Throwable throwable) {
		super(throwable);
	}
}
