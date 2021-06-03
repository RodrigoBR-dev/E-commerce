package org.serratec.ecommerce.exceptions;

public class CategoriaNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public CategoriaNotFoundException() {
		super();
	}

	public CategoriaNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CategoriaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CategoriaNotFoundException(String message) {
		super(message);
	}

	public CategoriaNotFoundException(Throwable cause) {
		super(cause);
	}
}
