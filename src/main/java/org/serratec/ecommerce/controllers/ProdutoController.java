package org.serratec.ecommerce.controllers;

import java.util.List;

import org.serratec.ecommerce.dto.ProdutoDTOCliente;
import org.serratec.ecommerce.dto.ProdutoDTOUsuario;
import org.serratec.ecommerce.exceptions.CategoriaNotFoundException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
import org.serratec.ecommerce.services.ProdutoService;
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
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoService service;
	
	@PostMapping
	public ResponseEntity<String> create(@RequestBody ProdutoDTOUsuario produto)
			throws CategoriaNotFoundException, ValorNegativoException {
		service.create(produto);
		return new ResponseEntity<>("Criado com sucesso", HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ProdutoDTOUsuario>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{nome}")
	public ResponseEntity<ProdutoDTOUsuario> findByNome(@PathVariable String nome) {
		return new ResponseEntity<>(service.findByNomeDTO(nome), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ProdutoDTOUsuario> update(@RequestBody ProdutoDTOUsuario produto)
			throws ProdutoNotFoundException, ValorNegativoException, CategoriaNotFoundException {
		return new ResponseEntity<>(service.update(produto), HttpStatus.OK);
	}

	@DeleteMapping("/{nome}")
	public ResponseEntity<String> delete(@PathVariable String nome) throws ProdutoNotFoundException {
		return new ResponseEntity<>(service.delete(nome), HttpStatus.OK);
	}

	@GetMapping("/cliente")
	public ResponseEntity<List<ProdutoDTOCliente>> findAllDTO() {
		return new ResponseEntity<>(service.findAllDTO(), HttpStatus.OK);
	}
}
