package org.serratec.ecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.dto.CategoriaDTOAll;
import org.serratec.ecommerce.entities.CategoriaEntity;
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
	CategoriaMapper  mapper;
	
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
		if (entity.isPresent()) {
			return mapper.entityToDTO(entity.get());
		}
		throw new CategoriaNotFoundException("Categoria não encontrada!");
	}
	
	public CategoriaDTO create(CategoriaDTO categoriaDto) {
		return mapper.entityToDTO(repository.save(mapper.dtoToEntity(categoriaDto)));
	}
	
	public String delete(String nome) throws CategoriaNotFoundException {
		CategoriaEntity categoria = findByNome(nome);
		repository.delete(categoria);
		return "Categoria deletada com sucesso!";
	}

	public CategoriaDTO update(CategoriaDTO categoriaNew) throws CategoriaNotFoundException {
		CategoriaEntity categoria = findByNome(categoriaNew.getNome());
		if (categoriaNew.getNovoNome() != null) {
			categoria.setNome(categoriaNew.getNovoNome());
		}
		if (categoriaNew.getDescricao() != null) {
			categoria.setDescricao(categoriaNew.getDescricao());
		}
		
		return mapper.entityToDTO(repository.save(categoria));
	}
}
