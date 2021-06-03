package org.serratec.ecommerce.controllers;

import java.util.List;

import org.serratec.ecommerce.entities.PedidoEntity;
import org.serratec.ecommerce.exceptions.PedidoNotFoundException;
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
	public ResponseEntity<List<PedidoEntity>> findAll() {
		return new ResponseEntity<List<PedidoEntity>>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PedidoEntity> findById(@PathVariable Long id) throws PedidoNotFoundException  {
		return new ResponseEntity<PedidoEntity>(service.getById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> create(@RequestBody PedidoEntity pedido) {
		service.create(pedido);
		return new ResponseEntity<String>("Criado com sucesso", HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PedidoEntity> update(@PathVariable Long id) throws PedidoNotFoundException{
		return new ResponseEntity<PedidoEntity>(service.update(id, null) , HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable  Long id) throws PedidoNotFoundException {
		service.delete(id);
		return new ResponseEntity<String>("Deletado com sucesso", HttpStatus.OK);
	}
}
