package org.serratec.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.CategoriaEntity;
import org.serratec.ecommerce.exceptions.CategoriaNotFoundException;
import org.serratec.ecommerce.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository repository;
	
	public List<CategoriaEntity> getAll(){
		return repository.findAll();
	}
	
	public CategoriaEntity getById(Long id) throws CategoriaNotFoundException {
		Optional<CategoriaEntity> categoria = repository.findById(id);
		if (categoria.isPresent()) {
			return categoria.get();
		}
		throw new CategoriaNotFoundException("Categoria não encontrada!");
	}
	
	public CategoriaEntity findByNome(String nome) throws CategoriaNotFoundException {
		Optional<CategoriaEntity> entity = repository.findByNome(nome);
		if (entity.isPresent()) {
			return entity.get();
		}
		throw new CategoriaNotFoundException("Categoria não encontrada!");
	}
	
	public CategoriaEntity create(CategoriaEntity categoria) {
		return repository.save(categoria);
	}
	
	public String delete(Long id) throws CategoriaNotFoundException {
		CategoriaEntity categoria = getById(id);
		repository.delete(categoria);
		return "Categoria deletada com sucesso!";
	}

	public CategoriaEntity update(CategoriaEntity categoriaNew) throws CategoriaNotFoundException {
		CategoriaEntity categoria = getById(categoriaNew.getId());
		if (categoriaNew.getNome() != null) {
			categoria.setNome(categoriaNew.getNome());
		}
		if (categoriaNew.getDescricao() != null) {
			categoria.setDescricao(categoriaNew.getDescricao());
		}
		
		return repository.save(categoria);
	}
}
