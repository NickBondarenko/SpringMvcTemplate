package com.alphatek.tylt.web.mvc.controller.error;

public final class AjaxControllerException extends ControllerException {
	public AjaxControllerException() {
		super();
	}

	public AjaxControllerException(String message) {
		super(message);
	}

	public AjaxControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public AjaxControllerException(Throwable cause) {
		super(cause);
	}

	protected AjaxControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}