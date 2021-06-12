package org.serratec.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.dto.CategoriaDTOAll;
import org.serratec.ecommerce.entities.CategoriaEntity;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.exceptions.CategoriaExistenteException;
import org.serratec.ecommerce.exceptions.CategoriaNotFoundException;
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
	
	public List<CategoriaDTOAll> getAll() {
		List<CategoriaEntity> listaCategoria = repository.findAllByAtivoTrue();
		List<CategoriaDTOAll> listaDTO = new ArrayList<>();
		for (CategoriaEntity categoriaEntity : listaCategoria) {
			listaDTO.add(mapper.entityToDTOAll(categoriaEntity));
		}
		return listaDTO;
	}
	
	public CategoriaEntity findByNome(String nome) throws CategoriaNotFoundException {
		Optional<CategoriaEntity> entity = repository.findByNome(nome);
		if (entity.isPresent()) {
			return entity.get();
		}
		throw new CategoriaNotFoundException("Categoria não encontrada!");
	}
	
	public CategoriaDTO findByNomeDTO(String nome) throws CategoriaNotFoundException {
		Optional<CategoriaEntity> entity = repository.findByAtivoTrueAndNome(nome);
		if (entity.isEmpty()) throw new CategoriaNotFoundException("Categoria não encontrada!");
		List<ProdutoEntity> produto = prodService.findAllByCategoria(entity.get());
		List<String> nomeProdutos = new ArrayList<>(); 
		for (ProdutoEntity produtoEntity : produto) {
			nomeProdutos.add(produtoEntity.getNome());
		}
		CategoriaDTO dto = mapper.entityToDTO(entity.get());
		dto.setProdutos(nomeProdutos);
		return dto;
	}
	
	public CategoriaDTOAll create(CategoriaDTO categoriaDto) throws CategoriaExistenteException {
		Optional<CategoriaEntity> categoria = repository.findByNome(categoriaDto.getNome());
		if (categoria.isEmpty()) {
			CategoriaEntity categoriaEntity = mapper.dtoToEntity(categoriaDto);
			categoriaEntity.setAtivo(true);
			return mapper.entityToDTOAll(repository.save(categoriaEntity));
		}
		throw new CategoriaExistenteException("A categoria que está tentando criar já existe!");
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
	
	public String delete(String nome) throws CategoriaNotFoundException {
		CategoriaEntity categoria = findByNome(nome);
		if (prodService.findAllByCategoria(categoria).isEmpty()) {
			repository.delete(categoria);
			return "Categoria deletada com sucesso!";
		} else {
			categoria.setAtivo(false);
			repository.save(categoria);
			return "Está categoria contém itens e por isso foi desativada!";
		}
	}
}
