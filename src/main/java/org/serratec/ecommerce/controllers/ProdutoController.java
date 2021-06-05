package org.serratec.ecommerce.controllers;

import java.util.List;

import org.serratec.ecommerce.dto.ProdutoDTO;
import org.serratec.ecommerce.entities.ProdutoEntity;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoService service;
	
	@PostMapping
	public ResponseEntity<String> create(@RequestBody ProdutoEntity produto){
		service.create(produto);
		return new ResponseEntity<String>("Criado com sucesso",HttpStatus.CREATED);
	}
	//Revisar: produto não deverá ser acessado pelo id pelo usuário
	/*
	 * @GetMapping("/{id}") public ResponseEntity<ProdutoEntity>
	 * findById(@PathVariable Long id) throws ProdutoNotFoundException{ return new
	 * ResponseEntity<ProdutoEntity>(service.findById(id),HttpStatus.OK); }
	 */
	@GetMapping
	public ResponseEntity<List<ProdutoEntity>> findAll(){
		return new ResponseEntity<List<ProdutoEntity>>(service.findAll(),HttpStatus.OK);
	}
	@GetMapping("/nome")
	public ResponseEntity <ProdutoEntity> findByNome(@RequestParam String nome){
		return new ResponseEntity<ProdutoEntity>(service.findByNome(nome),HttpStatus.OK);
	}
	@PutMapping("/{nome}")
	public ResponseEntity<ProdutoEntity> update(@PathVariable String nome,@RequestBody ProdutoEntity produto) throws ProdutoNotFoundException, ValorNegativoException{
		return new ResponseEntity<ProdutoEntity>(service.update(nome,produto),HttpStatus.OK);
	}
	@DeleteMapping("/{nome}")
	public ResponseEntity<String> delete(@PathVariable String nome) throws ProdutoNotFoundException{
		service.delete(nome);
		return new ResponseEntity<String>("Deletado com sucesso",HttpStatus.OK);
	}
	//DTO apenas com os campos nome,descricao e preço
	@GetMapping("/cliente")
	public ResponseEntity<List<ProdutoDTO>> findAllDTO(){
		return new ResponseEntity<List<ProdutoDTO>>(service.findAllDTO(),HttpStatus.OK);
	}
	
}
