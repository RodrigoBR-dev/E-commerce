package org.serratec.ecommerce.controllers;

import java.util.List;

import javax.mail.MessagingException;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.dto.PedidoDTOAll;
import org.serratec.ecommerce.dto.PedidoDTOComp;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.exceptions.EnderecoNotFoundException;
import org.serratec.ecommerce.exceptions.EstoqueInsuficienteException;
import org.serratec.ecommerce.exceptions.PedidoFinalizadoException;
import org.serratec.ecommerce.exceptions.PedidoNotFoundException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.exceptions.StatusUnacceptableException;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
import org.serratec.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	PedidoService service;
	
	@GetMapping
	public ResponseEntity<List<PedidoDTOAll>> findAll() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/cliente/{userName}")
	public ResponseEntity<List<PedidoDTOAll>> getByCliente(@PathVariable String userName) throws ClienteNotFoundException {
		return new ResponseEntity<>(service.getByCliente(userName), HttpStatus.OK);
	}
	
	@GetMapping("/{numeroDoPedido}")
	public ResponseEntity<PedidoDTOComp> findByNumeroDoPedido(@PathVariable Long numeroDoPedido) throws PedidoNotFoundException  {
		return new ResponseEntity<>(service.getByNumeroDTO(numeroDoPedido), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PedidoDTOComp> create(@RequestBody PedidoDTO pedido) throws ProdutoNotFoundException, ClienteNotFoundException, EnderecoNotFoundException, ValorNegativoException, EstoqueInsuficienteException, PedidoNotFoundException {
		return new ResponseEntity<>(service.create(pedido), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PedidoDTOComp> update(@RequestBody PedidoDTO pedido) throws PedidoNotFoundException, ProdutoNotFoundException, EstoqueInsuficienteException, StatusUnacceptableException, EnderecoNotFoundException, PedidoFinalizadoException, ValorNegativoException {
		return new ResponseEntity<>(service.update(pedido),  HttpStatus.OK);
	}
	
	@PutMapping("/pagamento/{numeroDoPedido}")
	public ResponseEntity<String> payment(@PathVariable Long numeroDoPedido) throws PedidoNotFoundException, StatusUnacceptableException, EnderecoNotFoundException, MessagingException {
		return new ResponseEntity<>(service.pagar(numeroDoPedido), HttpStatus.OK);
	}
	
	@PutMapping("/fechamento/{numeroDoPedido}")
	public ResponseEntity<String> close(@PathVariable Long numeroDoPedido) throws PedidoNotFoundException, EstoqueInsuficienteException,  StatusUnacceptableException, MessagingException {
		return new ResponseEntity<>(service.fechar(numeroDoPedido), HttpStatus.OK);
	}
	
	@PutMapping("/transporte/{numeroDoPedido}")
	public ResponseEntity<String> delivery(@PathVariable Long numeroDoPedido) throws PedidoNotFoundException, StatusUnacceptableException, MessagingException {
		return new ResponseEntity<>(service.transportar(numeroDoPedido), HttpStatus.OK);
	}
	
	@PutMapping("/finalizado/{numeroDoPedido}")
	public ResponseEntity<String> done(@PathVariable Long numeroDoPedido) throws PedidoNotFoundException, StatusUnacceptableException, MessagingException {
		return new ResponseEntity<>(service.entregar(numeroDoPedido), HttpStatus.OK);
	}
	
	@DeleteMapping("/{numeroDoPedido}")
	public ResponseEntity<String> delete(@PathVariable  Long numeroDoPedido) throws PedidoNotFoundException, PedidoFinalizadoException, EstoqueInsuficienteException, MessagingException {
		return new ResponseEntity<>(service.delete(numeroDoPedido), HttpStatus.OK);
	}
}