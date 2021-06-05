package org.serratec.ecommerce.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.dto.ProdutoDTO;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.exceptions.EstoqueInsuficienteException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
import org.serratec.ecommerce.mapper.ProdutoMapper;
import org.serratec.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository repository;
	@Autowired
	ProdutoMapper mapper;

	public void create(ProdutoEntity produto) {
		produto.setAtivo(true);
		produto.setDataCadastro(LocalDate.now());
		repository.save(produto);
		
	}

	public ProdutoEntity findById(Long id) throws ProdutoNotFoundException {		
		Optional<ProdutoEntity> produto = repository.findById(id);
		if(produto.isEmpty()) {
			throw new ProdutoNotFoundException("Produto não encontrado com esse id" + id);
		}
		return produto.get();
	}
	
	public List<ProdutoEntity> findByNome(String nome){		
		List<ProdutoEntity> produtos = repository.findByNome(nome);				
		return produtos;
	}

	public List<ProdutoEntity> findAll() {	
		return repository.findAll();
	}

	public ProdutoEntity update(Long id, ProdutoEntity produtoTemp) throws ProdutoNotFoundException, ValorNegativoException {
		ProdutoEntity produto = findById(id);
		if(produtoTemp.getNome() != null) {
			produto.setNome(produtoTemp.getNome());
		}
		if(produtoTemp.getCategoria() != null) {
			produto.setCategoria(produtoTemp.getCategoria());
		}
		if(produtoTemp.getDataCadastro() != null) {
			produto.setDataCadastro(produtoTemp.getDataCadastro());
		}
		if(produtoTemp.getPreco() != null) {
			produto.setPreco(produtoTemp.getPreco());
		}
		if(produtoTemp.getDescricao() != null) {
			produto.setDescricao(produtoTemp.getDescricao());
		}
		if(produtoTemp.getImagem() != null) {
			produto.setImagem(produtoTemp.getImagem());
		}
		if(produtoTemp.getQuantEstoque() != null) {
			produto.setQuantEstoque(produtoTemp.getQuantEstoque());
		}
		return repository.save(produto);
	}

	public void delete(Long id) throws ProdutoNotFoundException {
		ProdutoEntity produto = findById(id);
		produto.setAtivo(false);
		repository.save(produto);
	}

	public List<ProdutoDTO> findAllDTO() {
		List<ProdutoEntity> listaEntity = repository.findAll();
		List<ProdutoDTO> listaDTO = new ArrayList<>();
		for (ProdutoEntity elemento : listaEntity) {
			listaDTO.add(mapper.toProdutoDTOSimples(elemento));
		}
		return listaDTO;		
	}
	
	public void retornaEstoque(Long id,Integer estoque) throws ProdutoNotFoundException, ValorNegativoException {
		ProdutoEntity produto = findById(id);
		produto.setQuantEstoque(produto.getQuantEstoque()+estoque);
	}
	
	public void vender(Long id,Integer estoque) throws EstoqueInsuficienteException, ProdutoNotFoundException, ValorNegativoException {
		ProdutoEntity produto = findById(id);
		if(produto.getQuantEstoque() >= estoque) {
			produto.setQuantEstoque(produto.getQuantEstoque()-estoque);
			repository.save(produto);			
		}else {
			throw new EstoqueInsuficienteException("Estoque insuficiente");
		}
		
	}
	
	

}
