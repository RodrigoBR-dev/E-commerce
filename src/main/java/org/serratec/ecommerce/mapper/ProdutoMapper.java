package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.EnderecoDTO;
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
	
	public static EnderecoEntity enderecoViaDTOToEntity(EnderecoDTO enderecoDTO, EnderecoViaCEPDTO viaCEP) {
		var endereco = new EnderecoEntity();
		endereco.setCep(enderecoDTO.getCep());
		endereco.setRua(viaCEP.getLogradouro());
		endereco.setNumero(enderecoDTO.getNumero());
		endereco.setComplemento(enderecoDTO.getComplemento());
		endereco.setBairro(viaCEP.getBairro());
		endereco.setCidade(viaCEP.getLocalidade());
		endereco.setEstado(viaCEP.getUf());
		return endereco;
	}
}
