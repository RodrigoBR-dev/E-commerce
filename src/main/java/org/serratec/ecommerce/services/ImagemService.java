package org.serratec.ecommerce.services;

import java.io.IOException;
import java.util.Optional;

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
	public ImagemEntity create(String nome,MultipartFile file) throws IOException, ProdutoNotFoundException {
		Optional<ProdutoEntity> produto = Optional.ofNullable(prodRepository.findByNome(nome));
		if (produto.isEmpty()) {
			throw new ProdutoNotFoundException("Não existe produto com esse nome.");
		}		
		ImagemEntity imagem = new ImagemEntity();
		imagem.setNome("Imagem");
		imagem.setMimeType(file.getContentType());
		imagem.setData(file.getBytes());
		imagem.setProduto(produto.get());
		return repository.save(imagem);				
	}
	@Transactional
	public ImagemEntity getImagem(Long id) throws ProdutoNotFoundException {
//		ProdutoEntity produto = prodService.findByProdutoId(id);
		return repository.findById(1);
	}
	
}