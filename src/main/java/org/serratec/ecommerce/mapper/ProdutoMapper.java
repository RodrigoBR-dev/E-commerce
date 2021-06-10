package org.serratec.ecommerce.mapper;

import java.net.URI;

import org.serratec.ecommerce.dto.ProdutoDTOSimples;
import org.serratec.ecommerce.dto.ProdutoDTOUsuario;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ProdutoMapper {
	
	public ProdutoDTOSimples entityToProdDTOSimples(ProdutoEntity entity) {
		ProdutoDTOSimples dto = new ProdutoDTOSimples();
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		dto.setPreco(entity.getPreco());
		return dto;
	}
	public ProdutoEntity dtoSimplesToEntity(ProdutoDTOSimples dto) throws ValorNegativoException {
		ProdutoEntity entity = new ProdutoEntity();
		entity.setNome(dto.getNome());
		entity.setPreco(dto.getPreco());
		entity.setQuantEstoque(dto.getQuantEstoque());

		entity.setDescricao(dto.getDescricao());
		return entity;
	}
	
	public ProdutoDTOUsuario entityToDTOUsuario(ProdutoEntity entity) {
		ProdutoDTOUsuario usuario = new ProdutoDTOUsuario();
		URI uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("produto/{produtoNome}/imagem")
				.buildAndExpand(entity.getNome())
				.toUri();
		usuario.setNome(entity.getNome());
		usuario.setDescricao(entity.getDescricao());
		usuario.setDataCadastro(entity.getDataCadastro());
		usuario.setQuantEstoque(entity.getQuantEstoque());
		usuario.setPreco(entity.getPreco());
		usuario.setUrl(uri.toString());
		usuario.setCategoria(entity.getCategoria().getNome());
		return usuario;
	}
	public ProdutoEntity usuarioToEntity(ProdutoDTOUsuario usuario) throws ValorNegativoException {
		ProdutoEntity entity = new ProdutoEntity ();
		entity.setNome(usuario.getNome());
		entity.setDescricao(usuario.getDescricao());
		entity.setDataCadastro(usuario.getDataCadastro());
		entity.setQuantEstoque(usuario.getQuantEstoque());
		entity.setPreco(usuario.getPreco());

		return entity;
	}
}
