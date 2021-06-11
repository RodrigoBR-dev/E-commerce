package org.serratec.ecommerce.exceptions;

public class StatusUnacceptableException extends Exception {

	private static final long serialVersionUID = 1L;

	public StatusUnacceptableException() {
		super();
	}

	public StatusUnacceptableException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StatusUnacceptableException(String message, Throwable cause) {
		super(message, cause);
	}

	public StatusUnacceptableException(String message) {
		super(message);
	}

	public StatusUnacceptableException(Throwable cause) {
		super(cause);
	}
}
