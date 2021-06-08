package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.CategoriaDTO;
import org.serratec.ecommerce.dto.CategoriaDTOAll;
import org.serratec.ecommerce.entities.CategoriaEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
	
	public CategoriaDTOAll entityToDTOAll (CategoriaEntity entity) {
		var dto = new CategoriaDTOAll();
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		return dto;
	}

	public CategoriaDTO entityToDTO (CategoriaEntity entity) {
		var dto = new CategoriaDTO();
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		return dto;
	}
	
	public CategoriaEntity dtoToEntity (CategoriaDTO dto) {
		var entity = new CategoriaEntity();
		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
		return entity;
	}
	
	
}
