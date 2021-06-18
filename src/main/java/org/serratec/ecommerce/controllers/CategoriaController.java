package org.serratec.ecommerce.controllers;

import java.util.List;

import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.dto.CategoriaDTOAll;
import org.serratec.ecommerce.exceptions.CategoriaExistenteException;
import org.serratec.ecommerce.exceptions.CategoriaNotFoundException;
import org.serratec.ecommerce.services.CategoriaService;
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
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	CategoriaService service;
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTOAll>> getAll() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{nome}")
	public ResponseEntity<CategoriaDTO> findByNome(@PathVariable String nome) throws CategoriaNotFoundException {
		return new ResponseEntity<>(service.findByNomeDTO(nome), HttpStatus.OK);	
	}
	
	@PostMapping
	public ResponseEntity<CategoriaDTOAll> create(@RequestBody CategoriaDTO categoria) throws CategoriaNotFoundException, CategoriaExistenteException{
		return new ResponseEntity<>(service.create(categoria), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<CategoriaDTOAll> update(@RequestBody CategoriaDTO categoria) throws CategoriaNotFoundException{
		return new ResponseEntity<>(service.update(categoria), HttpStatus.OK);
	}
	
	@DeleteMapping("/{nome}")
	public ResponseEntity<String> delete(@PathVariable String nome) throws CategoriaNotFoundException {
		return new ResponseEntity<>(service.delete(nome), HttpStatus.OK);
	}
}
