package org.serratec.ecommerce.exceptions;

public class NomeEnderecoExistenteException extends Exception {

	private static final long serialVersionUID = 1L;

	public NomeEnderecoExistenteException() {
		super();
	}

	public NomeEnderecoExistenteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NomeEnderecoExistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public NomeEnderecoExistenteException(String message) {
		super(message);
	}

	public NomeEnderecoExistenteException(Throwable cause) {
		super(cause);
	}
}
