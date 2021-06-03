package org.serratec.ecommerce.util;

import org.serratec.ecommerce.exceptions.EnderecoNotFoundException;
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
}
