package org.serratec.ecommerce.controllers;

import java.util.List;

import org.serratec.ecommerce.dto.EnderecoDTO;
import org.serratec.ecommerce.entities.EnderecoEntity;
import org.serratec.ecommerce.exceptions.EnderecoNotFoundException;
import org.serratec.ecommerce.services.EnderecoService;
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
@RequestMapping("/endereco")
public class EnderecoController {

	@Autowired
	EnderecoService service;
	
	@GetMapping
	public ResponseEntity<List<EnderecoEntity>> getAll() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EnderecoEntity> findById(@PathVariable Long id) throws EnderecoNotFoundException {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<EnderecoEntity> create(@RequestBody EnderecoDTO dto) {
		return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<EnderecoEntity> update(@RequestBody EnderecoEntity entity) throws EnderecoNotFoundException {
		return new ResponseEntity<>(service.update(entity), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EnderecoNotFoundException {
		return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
	}
}
