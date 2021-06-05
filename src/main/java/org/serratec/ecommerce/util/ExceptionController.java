package org.serratec.ecommerce.util;

import org.serratec.ecommerce.exceptions.CategoriaNotFoundException;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.exceptions.EnderecoClienteNotAssociatedException;
import org.serratec.ecommerce.exceptions.EnderecoNotFoundException;
import org.serratec.ecommerce.exceptions.PedidoNotFoundException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
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
	@ExceptionHandler(CategoriaNotFoundException.class)
	public ResponseEntity<String> tratarLivroNotFoundException(CategoriaNotFoundException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}
	@ExceptionHandler(ClienteNotFoundException.class)
	public ResponseEntity<String> tratarLivroNotFoundException(ClienteNotFoundException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}
	@ExceptionHandler(PedidoNotFoundException.class)
	public ResponseEntity<String> tratarLivroNotFoundException(PedidoNotFoundException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}
	@ExceptionHandler(ProdutoNotFoundException.class)
	public ResponseEntity<String> tratarLivroNotFoundException(ProdutoNotFoundException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}
	
	@ExceptionHandler(EnderecoClienteNotAssociatedException.class)
	public ResponseEntity<String> tratarLivroNotFoundException(EnderecoClienteNotAssociatedException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}
}
