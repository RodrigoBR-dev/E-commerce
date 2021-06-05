package org.serratec.ecommerce.exceptions;

public class EnderecoClienteNotAssociatedException extends Exception {

	private static final long serialVersionUID = 1L;

	public EnderecoClienteNotAssociatedException() {
		super();
	}

	public EnderecoClienteNotAssociatedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EnderecoClienteNotAssociatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnderecoClienteNotAssociatedException(String message) {
		super(message);
	}

	public EnderecoClienteNotAssociatedException(Throwable cause) {
		super(cause);
	}

}
