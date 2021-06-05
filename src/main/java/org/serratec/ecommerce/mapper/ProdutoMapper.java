package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.EnderecoEntradaDTO;
import org.serratec.ecommerce.dto.EnderecoViaCEPDTO;
import org.serratec.ecommerce.dto.ProdutoDTO;
import org.serratec.ecommerce.entities.EnderecoEntity;
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
		entity.setDataCadastro(dto.getDataCadastro());
//		entity.setCategoria(dto.getCategoria());
		entity.setDescricao(dto.getDescricao());
		entity.setImagem(dto.getImagem());
		entity.setQuantEstoque(dto.getQuantEstoque());
		return entity;
	}
}
