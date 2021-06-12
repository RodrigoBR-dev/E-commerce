package org.serratec.ecommerce.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.ProdutoDTOUsuario;
import org.serratec.ecommerce.entities.CategoriaEntity;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.entities.ProdutosPedidosEntity;
import org.serratec.ecommerce.exceptions.CategoriaNotFoundException;
import org.serratec.ecommerce.exceptions.EstoqueInsuficienteException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
import org.serratec.ecommerce.mapper.ProdutoMapper;
import org.serratec.ecommerce.repositories.ProdutoRepository;
import org.serratec.ecommerce.repositories.ProdutosPedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository repository;
	@Autowired
	ProdutosPedidosRepository prodPedidosRepository;
	@Autowired
	ProdutoMapper mapper;
	@Autowired
	CategoriaService categoriaService;
	@Autowired
	ImagemService imagemService;

	public List<ProdutoDTOUsuario> findAll() {
		return repository.findAllByAtivoTrue().stream().map(mapper::entityToDTOUsuario).collect(Collectors.toList());
	}

	public ProdutoEntity findByNome(String nome) throws ProdutoNotFoundException {
		Optional<ProdutoEntity> produto = Optional.ofNullable(repository.findByAtivoTrueAndNome(nome));
		if (produto.isPresent())return produto.get();
		throw new ProdutoNotFoundException("Produto não encontrado!");
	}

	public ProdutoDTOUsuario findByNomeDTO(String nome) {
		ProdutoEntity produto = repository.findByAtivoTrueAndNome(nome);
		return mapper.entityToDTOUsuario(produto);
	}

	public List<ProdutoEntity> findAllByCategoria(CategoriaEntity categoria) {
		return repository.findAllByAtivoTrueAndCategoria(categoria);
	}

	public List<ProdutoDTOUsuario> findAllByCategoriaDTO(String categoriaNome) throws CategoriaNotFoundException {
		CategoriaEntity categoria = categoriaService.findByNome(categoriaNome);
		return findAllByCategoria(categoria).stream().map(mapper::entityToDTOUsuario).collect(Collectors.toList());
	}

	public ProdutoEntity findByNomeAll(String nome) throws ProdutoNotFoundException {
		ProdutoEntity findByNome = repository.findByNome(nome);
		if (findByNome.getNome() != null)
			return findByNome;
		throw new ProdutoNotFoundException("Produto não encontrado!");

	}

	public ProdutoDTOUsuario create(ProdutoDTOUsuario produto, MultipartFile file)throws CategoriaNotFoundException, ValorNegativoException, ProdutoNotFoundException, IOException, EstoqueInsuficienteException {
		Optional<ProdutoEntity> produtoEntity = Optional.ofNullable(repository.findByNome(produto.getNome()));
		if (produtoEntity.isPresent()) {
			update(produto, file);
			return produto;
		} else {
			CategoriaEntity categoria = categoriaService.findByNome(produto.getCategoria());
			ProdutoEntity prodEntity = mapper.usuarioToEntity(produto);
			prodEntity.setCategoria(categoria);
			prodEntity.setNome(prodEntity.getNome());
			prodEntity.setDataCadastro(LocalDate.now());
			repository.save(prodEntity);
			imagemService.create(prodEntity, file);
			return getImage(prodEntity);
		}
	}

	public ProdutoDTOUsuario update(ProdutoDTOUsuario produtoTemp) throws ProdutoNotFoundException, ValorNegativoException, CategoriaNotFoundException, EstoqueInsuficienteException {

		ProdutoEntity produto = findByNome(produtoTemp.getNome());
		produto.setAtivo(true);

		if (produtoTemp.getNovoNome() != null) {
			produto.setNome(produtoTemp.getNovoNome());
		}
		if (produtoTemp.getCategoria() != null) {
			CategoriaEntity categoria = categoriaService.findByNome(produtoTemp.getCategoria());
			produto.setCategoria(categoria);
		}
		if (produtoTemp.getDataCadastro() != null) {
			produto.setDataCadastro(LocalDate.now());
		}

		if (produtoTemp.getPreco() != null) {
			produto.setPreco(produtoTemp.getPreco());
		}
		if (produtoTemp.getDescricao() != null) {
			produto.setDescricao(produtoTemp.getDescricao());
		}
		if (produtoTemp.getQuantEstoque() != null) {
			produto.setQuantEstoque(produtoTemp.getQuantEstoque());
		}
		return mapper.entityToDTOUsuario(repository.save(produto));
	}
	
	public ProdutoDTOUsuario update(ProdutoDTOUsuario produtoTemp, MultipartFile file) throws ProdutoNotFoundException, ValorNegativoException, CategoriaNotFoundException, EstoqueInsuficienteException, IOException {
		
		ProdutoEntity produto = findByNome(produtoTemp.getNome());
		produto.setAtivo(true);
		imagemService.delete(produto);
		imagemService.create(produto, file);
		
		if (produtoTemp.getNovoNome() != null) {
			produto.setNome(produtoTemp.getNovoNome());
		}
		if (produtoTemp.getCategoria() != null) {
			CategoriaEntity categoria = categoriaService.findByNome(produtoTemp.getCategoria());
			produto.setCategoria(categoria);
		}
		if (produtoTemp.getDataCadastro() != null) {
			produto.setDataCadastro(LocalDate.now());
		}
		
		if (produtoTemp.getPreco() != null) {
			produto.setPreco(produtoTemp.getPreco());
		}
		if (produtoTemp.getDescricao() != null) {
			produto.setDescricao(produtoTemp.getDescricao());
		}
		if (produtoTemp.getQuantEstoque() != null) {
			produto.setQuantEstoque(produtoTemp.getQuantEstoque());
		}
		return mapper.entityToDTOUsuario(repository.save(produto));
	}

	public String delete(String nome) throws ProdutoNotFoundException {
		ProdutoEntity produto = findByNome(nome);
		List<ProdutosPedidosEntity> listaProdPedidos = prodPedidosRepository.findByProduto(produto);
		if(listaProdPedidos.isEmpty()) {
			imagemService.delete(produto);
			repository.delete(produto);
		}else {
			produto.setAtivo(false);
			repository.save(produto);
		}		
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

	public void retornaEstoque(ProdutoEntity produto, Integer estoque) throws EstoqueInsuficienteException {
		produto.setQuantEstoque(produto.getQuantEstoque() + estoque);
		repository.save(produto);
	}


	public ProdutoDTOUsuario getImage(ProdutoEntity entity) {
		var uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("produto/{produtoNome}/imagem")
				.buildAndExpand(entity.getNome()).toUri();
		ProdutoDTOUsuario prodDTO = mapper.entityToDTOUsuario(entity);
		prodDTO.setUrl(uri.toString());
		return prodDTO;
	}
}