package org.serratec.ecommerce.exceptions;

public class TokeExpiredException extends Exception {

	private static final long serialVersionUID = 1L;

	public TokeExpiredException() {
		super();
	}

	public TokeExpiredException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TokeExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokeExpiredException(String message) {
		super(message);
	}

	public TokeExpiredException(Throwable cause) {
		super(cause);
	}
}
