package org.serratec.ecommerce.util;

import org.serratec.ecommerce.exceptions.EnderecoNotFoundException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	private static final String MSG = "x-error-msg";
	
	@ExceptionHandler(EnderecoNotFoundException.class)
	public ResponseEntity<String> tratarLivroNotFoundException(EnderecoNotFoundException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}
	@ExceptionHandler(ValorNegativoException.class)
	public ResponseEntity<String> valorNegativo(ValorNegativoException exception) {
		return ResponseEntity.badRequest()
				.header(MSG, exception.getMessage())
				.build();
	}
	@ExceptionHandler(ProdutoNotFoundException.class)
	public ResponseEntity<String> produtoNotFound(ProdutoNotFoundException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}
	
}
