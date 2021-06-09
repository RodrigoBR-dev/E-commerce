package org.serratec.ecommerce.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.ProdutoDTOCliente;
import org.serratec.ecommerce.dto.ProdutoDTOUsuario;
import org.serratec.ecommerce.entities.CategoriaEntity;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.exceptions.CategoriaNotFoundException;
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
	@Autowired
	CategoriaService categoriaService;

	public List<ProdutoDTOUsuario> findAll() {
		return repository.findAll().stream().map(mapper::toDTOUsuario).collect(Collectors.toList());
	}

	public List<ProdutoDTOCliente> findAllDTO() {
		List<ProdutoEntity> listaEntity = repository.findAllByAtivoTrue();
		List<ProdutoDTOCliente> listaDTO = new ArrayList<>();
		for (ProdutoEntity elemento : listaEntity) {
			listaDTO.add(mapper.toProdutoDTOSimples(elemento));
		}
		return listaDTO;
	}

	public ProdutoEntity findByNome(String nome) throws ProdutoNotFoundException {
		ProdutoEntity produto = repository.findByAtivoTrueAndNome(nome);
		if (produto.getNome() != null) return produto;
		throw new ProdutoNotFoundException("Produto não encontrado!");
	}

	public ProdutoDTOUsuario findByNomeDTO(String nome) {
		ProdutoEntity produto = repository.findByAtivoTrueAndNome(nome);
		return mapper.toDTOUsuario(produto);
	}

	public List<ProdutoEntity> findAllByCategoria(CategoriaEntity categoria) {
		return repository.findAllByAtivoTrueAndCategoria(categoria);
	}

	public void create(ProdutoDTOUsuario produto) throws CategoriaNotFoundException, ValorNegativoException {
		CategoriaEntity categoria = categoriaService.findByNome(produto.getCategoria());
		ProdutoEntity prodEntity = mapper.usuarioToEntity(produto);
		prodEntity.setAtivo(true);
		prodEntity.setDataCadastro(LocalDate.now());
		prodEntity.setCategoria(categoria);
		repository.save(prodEntity);

	}

	public ProdutoDTOUsuario update(ProdutoDTOUsuario produtoTemp)
			throws ProdutoNotFoundException, ValorNegativoException, CategoriaNotFoundException {
		ProdutoEntity produto = findByNome(produtoTemp.getNome());

		if (produtoTemp.getNome() != null) {
			produto.setNome(produtoTemp.getNome());
		}
		if (produtoTemp.getCategoria() != null) {
			CategoriaEntity categoria = categoriaService.findByNome(produtoTemp.getCategoria());
			produto.setCategoria(categoria);
		}
		if (produtoTemp.getDataCadastro() != null) {
			produto.setDataCadastro(produtoTemp.getDataCadastro());
		}
		if (produtoTemp.getPreco() != null) {
			produto.setPreco(produtoTemp.getPreco());
		}
		if (produtoTemp.getDescricao() != null) {
			produto.setDescricao(produtoTemp.getDescricao());
		}
		if (produtoTemp.getImagem() != null) {
			produto.setImagem(produtoTemp.getImagem());
		}
		if (produtoTemp.getQuantEstoque() != null) {
			produto.setQuantEstoque(produtoTemp.getQuantEstoque());
		}

		return mapper.toDTOUsuario(repository.save(produto));
	}

	public String delete(String nome) throws ProdutoNotFoundException {
		ProdutoEntity produto = findByNome(nome);
		produto.setAtivo(false);
		repository.save(produto);
		return "Deletado com sucesso";
	}

	public void vender(ProdutoEntity produto, Integer estoque) throws EstoqueInsuficienteException {
		if (produto.getQuantEstoque() >= estoque) {
			produto.setQuantEstoque(produto.getQuantEstoque() - estoque);
			repository.save(produto);
		} else {
			throw new EstoqueInsuficienteException("Estoque insuficiente");
		}
	}

	public void retornaEstoque(ProdutoEntity produto, Integer estoque) {
		produto.setQuantEstoque(produto.getQuantEstoque() + estoque);
		repository.save(produto);
	}
}
