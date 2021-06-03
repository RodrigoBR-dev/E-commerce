package org.serratec.ecommerce.exceptions;

public class PedidoNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4721214538220882873L;

	public PedidoNotFoundException() {
		super();
	}

	public PedidoNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PedidoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PedidoNotFoundException(String message) {
		super(message);
	}

	public PedidoNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
