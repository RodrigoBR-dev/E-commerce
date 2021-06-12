package org.serratec.ecommerce.exceptions;

public class DisabledClienteException extends Exception {

	private static final long serialVersionUID = 1L;

	public DisabledClienteException() {
		super();
	}

	public DisabledClienteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DisabledClienteException(String message, Throwable cause) {
		super(message, cause);
	}

	public DisabledClienteException(String message) {
		super(message);
	}

	public DisabledClienteException(Throwable cause) {
		super(cause);
	}
}
