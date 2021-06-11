package org.serratec.ecommerce.exceptions;

public class CategoriaExistenteException extends Exception {

	private static final long serialVersionUID = 1L;

	public CategoriaExistenteException() {
		super();
	}

	public CategoriaExistenteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CategoriaExistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public CategoriaExistenteException(String message) {
		super(message);
	}

	public CategoriaExistenteException(Throwable cause) {
		super(cause);
	}
}
