package org.serratec.ecommerce.services;

import java.io.IOException;

import org.serratec.ecommerce.entities.ImagemEntity;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.repositories.ImagemRepository;
import org.serratec.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagemService {

	@Autowired 
	ImagemRepository repository;
	
	@Autowired
	ProdutoRepository prodRepository;
	
	@Autowired
	ProdutoService prodService;
	
	@Transactional
	public ImagemEntity create(ProdutoEntity produto,MultipartFile file) throws IOException {
		var imagem = new ImagemEntity();
		imagem.setNome("Imagem");
		imagem.setMimeType(file.getContentType());
		imagem.setData(file.getBytes());
		imagem.setProduto(produto);
		return repository.save(imagem);				
	}
	
	@Transactional
	public ImagemEntity getImagem(String nome) throws ProdutoNotFoundException {
		ProdutoEntity produto = prodService.findByNome(nome);
		return repository.findByProduto(produto);
	}
	
	@Transactional
	public void delete(ProdutoEntity produto) {
		repository.delete(repository.findByProduto(produto));
	}
}