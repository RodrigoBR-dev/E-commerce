package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.ProdutoDTOSimples;
import org.serratec.ecommerce.dto.ProdutoDTOUsuario;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.exceptions.EstoqueInsuficienteException;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ProdutoMapper {
	
	public ProdutoDTOSimples entityToProdDTOSimples(ProdutoEntity entity) {
		var dto = new ProdutoDTOSimples();
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		dto.setPreco(entity.getPreco());
		return dto;
	}
	public ProdutoEntity dtoSimplesToEntity(ProdutoDTOSimples dto) throws ValorNegativoException, EstoqueInsuficienteException {
		var entity = new ProdutoEntity();
		entity.setNome(dto.getNome());
		entity.setPreco(dto.getPreco());
		entity.setQuantEstoque(dto.getQuantEstoque());

		entity.setDescricao(dto.getDescricao());
		return entity;
	}
	
	public ProdutoDTOUsuario entityToDTOUsuario(ProdutoEntity entity) {
		var dto = new ProdutoDTOUsuario();
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		dto.setDataCadastro(entity.getDataCadastro());
		dto.setQuantEstoque(entity.getQuantEstoque());
		dto.setPreco(entity.getPreco());
		dto.setUrl(this.geraUrl(dto.getNome()));
		dto.setCategoria(entity.getCategoria().getNome());
		return dto;
	}
	
	public String geraUrl(String nomeProduto) {
		var uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("produto/{produtoNome}/imagem")
				.buildAndExpand(nomeProduto)
				.toUri();
		return uri.toString();
	}
	
	public ProdutoEntity usuarioToEntity(ProdutoDTOUsuario usuario) throws ValorNegativoException, EstoqueInsuficienteException {
		var entity = new ProdutoEntity ();
		entity.setNome(usuario.getNome());
		entity.setDescricao(usuario.getDescricao());
		entity.setDataCadastro(usuario.getDataCadastro());
		entity.setQuantEstoque(usuario.getQuantEstoque());
		entity.setPreco(usuario.getPreco());

		return entity;
	}
}