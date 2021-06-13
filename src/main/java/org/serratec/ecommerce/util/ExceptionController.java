package org.serratec.ecommerce.util;

import org.serratec.ecommerce.exceptions.AtributoEncontradoException;
import org.serratec.ecommerce.exceptions.CategoriaExistenteException;
import org.serratec.ecommerce.exceptions.CategoriaNotFoundException;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.exceptions.DisabledClienteException;
import org.serratec.ecommerce.exceptions.EnderecoClienteNotAssociatedException;
import org.serratec.ecommerce.exceptions.EnderecoNotFoundException;
import org.serratec.ecommerce.exceptions.EstoqueInsuficienteException;
import org.serratec.ecommerce.exceptions.InvalidTokenException;
import org.serratec.ecommerce.exceptions.NomeEnderecoExistenteException;
import org.serratec.ecommerce.exceptions.NotclosedPedidoException;
import org.serratec.ecommerce.exceptions.PedidoFinalizadoException;
import org.serratec.ecommerce.exceptions.PedidoNotFoundException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.exceptions.StatusUnacceptableException;
import org.serratec.ecommerce.exceptions.TokeExpiredException;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
import org.serratec.ecommerce.exceptions.ViaCEPUnreachableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	private static final String MSG = "x-error-msg";
	
	@ExceptionHandler(EnderecoNotFoundException.class)
	public ResponseEntity<String> tratarEnderecoNotFoundException(EnderecoNotFoundException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<String> tratarInvalidTokenException(InvalidTokenException exception) {
		return ResponseEntity.badRequest()
				.header(MSG, exception.getMessage())
				.build();
	}

	@ExceptionHandler(CategoriaNotFoundException.class)
	public ResponseEntity<String> tratarCategoriaNotFoundException(CategoriaNotFoundException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}
	@ExceptionHandler(ClienteNotFoundException.class)
	public ResponseEntity<String> tratarClienteNotFoundException(ClienteNotFoundException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}

	@ExceptionHandler(PedidoNotFoundException.class)
	public ResponseEntity<String> tratarPedidoNotFoundException(PedidoNotFoundException exception) {
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

	@ExceptionHandler(EnderecoClienteNotAssociatedException.class)
	public ResponseEntity<String> tratarEnderecoClienteNotAssociatedException(EnderecoClienteNotAssociatedException exception) {
		return ResponseEntity.badRequest()
				.header(MSG, exception.getMessage())
				.build();
	}
	
	@ExceptionHandler(ViaCEPUnreachableException.class)
	public ResponseEntity<String> tratarviaCEPUnreachebleException(ViaCEPUnreachableException exception) {
		return ResponseEntity.notFound()
				.header(MSG, exception.getMessage())
				.build();
	}
	
	@ExceptionHandler(EstoqueInsuficienteException.class)
	public ResponseEntity<String> tratarEstoqueInsuficienteException(EstoqueInsuficienteException exception) {
		return ResponseEntity.badRequest()
				.header(MSG, exception.getMessage())
				.build();
	}

	@ExceptionHandler(NotclosedPedidoException.class)
	public ResponseEntity<String> tratarNotclosedPedidoException(NotclosedPedidoException exception) {
		return ResponseEntity.badRequest()
				.header(MSG, exception.getMessage())
				.build();
	}
	
	@ExceptionHandler(PedidoFinalizadoException.class)
	public ResponseEntity<String> tratarPedidoFinalizadoException(PedidoFinalizadoException exception) {
		return ResponseEntity.badRequest()
				.header(MSG, exception.getMessage())
				.build();
	}
	
	@ExceptionHandler(StatusUnacceptableException.class)
	public ResponseEntity<String> tratarStatusUnacceptableException(StatusUnacceptableException exception) {
		return ResponseEntity.badRequest()
				.header(MSG, exception.getMessage())
				.build();
	}
	
	@ExceptionHandler(AtributoEncontradoException.class)
	public ResponseEntity<String> tratarAtributoEncontradoException(AtributoEncontradoException exception) {
		return ResponseEntity.badRequest()
				.header(MSG, exception.getMessage())
				.build();
	}
	
	@ExceptionHandler(NomeEnderecoExistenteException.class)
	public ResponseEntity<String> tratarNomeEnderecoExistenteException(NomeEnderecoExistenteException exception) {
		return ResponseEntity.badRequest()
				.header(MSG, exception.getMessage())
				.build();
	}

	@ExceptionHandler(CategoriaExistenteException.class)
	public ResponseEntity<String> tratarCategoriaExistenteException(CategoriaExistenteException exception) {
		return ResponseEntity.badRequest()
				.header(MSG, exception.getMessage())
				.build();
	}
	
	@ExceptionHandler(DisabledClienteException.class)
	public ResponseEntity<String> tratarDisabledClienteException(DisabledClienteException exception) {
		return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED)
				.header(MSG, exception.getMessage())
				.build();
	}
	
	@ExceptionHandler(TokeExpiredException.class)
	public ResponseEntity<String> tratarTokeExpiredException(TokeExpiredException exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.header(MSG, exception.getMessage())
				.build();
	}

}
