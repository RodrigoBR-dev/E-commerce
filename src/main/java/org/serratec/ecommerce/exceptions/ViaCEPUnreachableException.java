package org.serratec.ecommerce.exceptions;

public class ViaCEPUnreachableException extends Exception {

	private static final long serialVersionUID = 1L;

	public ViaCEPUnreachableException() {
		super();
	}

	public ViaCEPUnreachableException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ViaCEPUnreachableException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViaCEPUnreachableException(String message) {
		super(message);
	}

	public ViaCEPUnreachableException(Throwable cause) {
		super(cause);
	}
}
