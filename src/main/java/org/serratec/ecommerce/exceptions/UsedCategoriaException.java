package org.serratec.ecommerce.exceptions;

public class UsedCategoriaException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsedCategoriaException() {
		super();
	}

	public UsedCategoriaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UsedCategoriaException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsedCategoriaException(String message) {
		super(message);
	}

	public UsedCategoriaException(Throwable cause) {
		super(cause);
	}
}
