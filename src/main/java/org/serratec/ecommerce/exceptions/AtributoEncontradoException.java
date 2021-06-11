package org.serratec.ecommerce.exceptions;

public class AtributoEncontradoException extends Exception {

	private static final long serialVersionUID = 1L;

	public AtributoEncontradoException() {
		super();
	}

	public AtributoEncontradoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AtributoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

	public AtributoEncontradoException(String message) {
		super(message);
	}

	public AtributoEncontradoException(Throwable cause) {
		super(cause);
	}	

}
