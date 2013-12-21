package com.alphatek.tylt.web.servlet.mvc.controller.error;

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