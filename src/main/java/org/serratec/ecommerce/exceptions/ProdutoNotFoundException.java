package org.serratec.ecommerce.exceptions;

public class ProdutoNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProdutoNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProdutoNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ProdutoNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ProdutoNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ProdutoNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
