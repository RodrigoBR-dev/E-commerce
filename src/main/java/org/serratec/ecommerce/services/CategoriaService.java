package org.serratec.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.dto.CategoriaDTOAll;
import org.serratec.ecommerce.entities.CategoriaEntity;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.exceptions.CategoriaNotFoundException;
import org.serratec.ecommerce.exceptions.UsedCategoriaException;
import org.serratec.ecommerce.mapper.CategoriaMapper;
import org.serratec.ecommerce.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository repository;
	
	@Autowired
	CategoriaMapper mapper;
	
	@Autowired
	ProdutoService prodService;
	
	public List<CategoriaDTOAll> getAll(){
		return repository.findAll().stream().map(mapper::entityToDTOAll).collect(Collectors.toList());
	}
	
	public CategoriaEntity findByNome(String nome) throws CategoriaNotFoundException {
		Optional<CategoriaEntity> entity = repository.findByNome(nome);
		if (entity.isPresent()) {
			return entity.get();
		}
		throw new CategoriaNotFoundException("Categoria não encontrada!");
	}
	
	public CategoriaDTO findByNomeDTO(String nome) throws CategoriaNotFoundException {
		Optional<CategoriaEntity> entity = repository.findByNome(nome);
		System.out.println(entity.get().getNome());
		if (entity.isEmpty()) throw new CategoriaNotFoundException("Categoria não encontrada!");
		List<ProdutoEntity> produto = prodService.findAllByCategoria(entity.get());
		
		System.out.println(produto);
// .stream().map(ProdutoEntity::getNome).collect(Collectors.toList());
		List<String> nomeProdutos = new ArrayList<>(); 
		for (ProdutoEntity produtoEntity : produto) {
			nomeProdutos.add(produtoEntity.getNome());
		}
		CategoriaDTO dto = mapper.entityToDTO(entity.get());
		dto.setProdutos(nomeProdutos);
		return dto;
	}
	
	public CategoriaDTOAll create(CategoriaDTO categoriaDto) {
		return mapper.entityToDTOAll(repository.save(mapper.dtoToEntity(categoriaDto)));
	}

	public CategoriaDTOAll update(CategoriaDTO categoriaNew) throws CategoriaNotFoundException {
		CategoriaEntity categoria = findByNome(categoriaNew.getNome());
		if (categoriaNew.getNovoNome() != null) {
			categoria.setNome(categoriaNew.getNovoNome());
		}
		if (categoriaNew.getDescricao() != null) {
			categoria.setDescricao(categoriaNew.getDescricao());
		}
		
		return mapper.entityToDTOAll(repository.save(categoria));
	}
	
	public String delete(String nome) throws CategoriaNotFoundException, UsedCategoriaException {
		CategoriaEntity categoria = findByNome(nome);
		if (prodService.findAllByCategoria(categoria) == null) {
			repository.delete(categoria);
			return "Categoria deletada com sucesso!";
		}
		throw new UsedCategoriaException("Está categoria contém itens e não pode ser deletada!");
	}
}
