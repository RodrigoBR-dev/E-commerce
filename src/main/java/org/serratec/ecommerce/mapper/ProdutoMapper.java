package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.ProdutoDTO;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
	public ProdutoDTO toProdutoDTOSimples(ProdutoEntity entity) {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		dto.setPreco(entity.getPreco());
		return dto;
	}
	public ProdutoEntity toEntity(ProdutoDTO dto) {
		ProdutoEntity entity = new ProdutoEntity();
		entity.setNome(dto.getNome());
		entity.setPreco(dto.getPreco());

		return entity;
	}
}
