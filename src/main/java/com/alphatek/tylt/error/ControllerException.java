package com.alphatek.tylt.error;

/**
 * Created with IntelliJ IDEA.
 * User: jason.dimeo
 * Date: 5/10/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ControllerException extends RuntimeException {
	public ControllerException() {
		super();
	}

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}

	protected ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}