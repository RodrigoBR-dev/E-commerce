package org.serratec.ecommerce.controllers;

import java.io.IOException;
import java.util.List;

import org.serratec.ecommerce.dto.ProdutoDTOSimples;
import org.serratec.ecommerce.dto.ProdutoDTOUsuario;
import org.serratec.ecommerce.entities.ImagemEntity;
import org.serratec.ecommerce.exceptions.CategoriaNotFoundException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
import org.serratec.ecommerce.services.ImagemService;
import org.serratec.ecommerce.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	ProdutoService service;
	
	@Autowired
	ImagemService imagemService;
	
	@PostMapping
	public ResponseEntity<ProdutoDTOUsuario> create(@RequestPart ProdutoDTOUsuario produto,@RequestParam MultipartFile file) throws CategoriaNotFoundException, ValorNegativoException, ProdutoNotFoundException, IOException{
		return new ResponseEntity<>(service.create(produto,file),HttpStatus.CREATED);
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
	public ResponseEntity<ProdutoDTOUsuario> update(@RequestBody ProdutoDTOUsuario produto )
			throws ProdutoNotFoundException, ValorNegativoException, CategoriaNotFoundException {
		return new ResponseEntity<>(service.update(produto), HttpStatus.OK);
	}

	@DeleteMapping("/{nome}")
	public ResponseEntity<String> delete(@PathVariable String nome) throws ProdutoNotFoundException {
		return new ResponseEntity<>(service.delete(nome), HttpStatus.OK);
	}

	@GetMapping("/cliente")
	public ResponseEntity<List<ProdutoDTOSimples>> findAllDTO(){
		return new ResponseEntity<List<ProdutoDTOSimples>>(service.findAllDTO(),HttpStatus.OK);
	}
	@GetMapping("/categoria/{categoria}")
	public ResponseEntity<List<ProdutoDTOUsuario>> findByCategoria(@PathVariable String categoria) throws CategoriaNotFoundException{
		return new ResponseEntity<List<ProdutoDTOUsuario>>(service.findAllByCategoriaDTO(categoria),HttpStatus.OK);

	}
	@GetMapping("/produto/{produtoNome}/imagem")
	public ResponseEntity<byte[]> getImagem(@PathVariable String produtoNome){
		ImagemEntity imagem = imagemService.getImagem(produtoNome);
		HttpHeaders header = new HttpHeaders();
		header.add("content-length", String.valueOf(imagem.getData().length));
		header.add("content-type", imagem.getMimeType());
		
		return new ResponseEntity<byte[]>(imagem.getData(),header,HttpStatus.OK);
		
	}
}