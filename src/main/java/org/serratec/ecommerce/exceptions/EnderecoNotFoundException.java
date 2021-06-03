package org.serratec.ecommerce.exceptions;

public class EnderecoNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public EnderecoNotFoundException() {
		super();
	}

	public EnderecoNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EnderecoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnderecoNotFoundException(String message) {
		super(message);
	}

	public EnderecoNotFoundException(Throwable cause) {
		super(cause);
	}
}
