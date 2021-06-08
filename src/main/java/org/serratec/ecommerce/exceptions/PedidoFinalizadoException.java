package org.serratec.ecommerce.exceptions;

public class PedidoFinalizadoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5695576605382086696L;

	public PedidoFinalizadoException() {
		super();
	}

	public PedidoFinalizadoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PedidoFinalizadoException(String message, Throwable cause) {
		super(message, cause);
	}

	public PedidoFinalizadoException(String message) {
		super(message);
	}

	public PedidoFinalizadoException(Throwable cause) {
		super(cause);
	}
	
	
}
