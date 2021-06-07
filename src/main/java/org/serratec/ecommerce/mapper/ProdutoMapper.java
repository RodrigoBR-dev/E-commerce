package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.ProdutoDTOCliente;
import org.serratec.ecommerce.dto.ProdutoDTOUsuario;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
	
	public ProdutoDTOCliente toProdutoDTOSimples(ProdutoEntity entity) {
		ProdutoDTOCliente dto = new ProdutoDTOCliente();
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		dto.setPreco(entity.getPreco());
		return dto;
	}
	public ProdutoEntity dtoClienteToEntity(ProdutoDTOCliente dto) throws ValorNegativoException {
		ProdutoEntity entity = new ProdutoEntity();
		entity.setNome(dto.getNome());
		entity.setPreco(dto.getPreco());

		return entity;
	}
	
	public ProdutoDTOUsuario toDTOUsuario(ProdutoEntity entity) {
		ProdutoDTOUsuario usuario = new ProdutoDTOUsuario();
		usuario.setNome(entity.getNome());
		usuario.setDescricao(entity.getDescricao());
		usuario.setDataCadastro(entity.getDataCadastro());
		usuario.setQuantEstoque(entity.getQuantEstoque());
		usuario.setPreco(entity.getPreco());
		usuario.setImagem(entity.getImagem());
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
		entity.setImagem(usuario.getImagem());
		return entity;
	}
}
