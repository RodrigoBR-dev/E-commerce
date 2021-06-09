package org.serratec.ecommerce.controllers;

import java.util.List;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.entities.PedidoEntity;
import org.serratec.ecommerce.exceptions.EstoqueInsuficienteException;
import org.serratec.ecommerce.exceptions.NotclosedPedidoException;
import org.serratec.ecommerce.exceptions.PedidoFinalizadoException;
import org.serratec.ecommerce.exceptions.PedidoNotFoundException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	PedidoService service;
	
	@GetMapping
	public ResponseEntity<List<PedidoDTO>> findAll() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{numeroDoPedido}")
	public ResponseEntity<PedidoEntity> findByNumero(@PathVariable Long numeroDoPedido) throws PedidoNotFoundException  {
		return new ResponseEntity<>(service.getByNumero(numeroDoPedido), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> create(@RequestBody PedidoDTO pedido) throws ProdutoNotFoundException {
		return new ResponseEntity<>(service.create(pedido), HttpStatus.CREATED);
	}
	
	@PutMapping
	public void update(@RequestBody PedidoDTO pedido) throws PedidoNotFoundException, ProdutoNotFoundException, EstoqueInsuficienteException {
		service.update(pedido);
	}
	
	@PutMapping("/pagamento/{numeroDoPedido}")
	public ResponseEntity<String> payment(@PathVariable Long numeroDoPedido) throws PedidoNotFoundException, NotclosedPedidoException {
		return new ResponseEntity<>(service.pagar(numeroDoPedido), HttpStatus.OK);
	}
	
	@PutMapping("/transporte/{numeroDoPedido}")
	public ResponseEntity<String> delivery(@PathVariable Long numeroDoPedido) throws PedidoNotFoundException {
		return new ResponseEntity<>(service.transportar(numeroDoPedido), HttpStatus.OK);
	}
	
	@PutMapping("/finalizado/{numeroDoPedido}")
	public ResponseEntity<String> done(@PathVariable Long numeroDoPedido) throws PedidoNotFoundException {
		return new ResponseEntity<>(service.entregar(numeroDoPedido), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable  Long id) throws PedidoNotFoundException, PedidoFinalizadoException {
		return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
	}
}
