package org.serratec.ecommerce.controllers;

import java.util.List;

import org.serratec.ecommerce.entities.ClienteEntity;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.services.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	ClienteService service;
	
	@GetMapping
	public ResponseEntity<List<ClienteEntity>> getAll() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteEntity> findById(@PathVariable Long id) throws ClienteNotFoundException {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ClienteEntity> create(@RequestBody ClienteEntity entity) {
		return new ResponseEntity<>(service.create(entity), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<ClienteEntity> update(@RequestBody ClienteEntity entity) throws ClienteNotFoundException {
		return new ResponseEntity<>(service.update(entity), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws ClienteNotFoundException {
		return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
	}
}
