package org.serratec.ecommerce.exceptions;

public class NotclosedPedidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotclosedPedidoException() {
		super();
	}

	public NotclosedPedidoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotclosedPedidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotclosedPedidoException(String message) {
		super(message);
	}

	public NotclosedPedidoException(Throwable cause) {
		super(cause);
	}
}
