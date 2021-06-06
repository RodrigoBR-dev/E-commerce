package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.EnderecoDTONovo;
import org.serratec.ecommerce.dto.EnderecoDTOComp;
import org.serratec.ecommerce.dto.EnderecoViaCEPDTO;
import org.serratec.ecommerce.entities.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

	public EnderecoEntity enderecoViaDTOToEntity(EnderecoDTONovo enderecoDTO, EnderecoViaCEPDTO viaCEP) {
		var endereco = new EnderecoEntity();
		endereco.setCep(enderecoDTO.getCep());
		endereco.setRua(viaCEP.getLogradouro());
		endereco.setNumero(enderecoDTO.getNumero());
		endereco.setComplemento(enderecoDTO.getComplemento());
		endereco.setBairro(viaCEP.getBairro());
		endereco.setCidade(viaCEP.getLocalidade());
		endereco.setEstado(viaCEP.getUf());
		endereco.setNome(enderecoDTO.getNome());
		return endereco;
	}
	
	public EnderecoDTOComp entityToEnderecoDTOComp(EnderecoEntity entity) {
			var dto = new EnderecoDTOComp();
			if (entity.getCliente() == null) return dto;
			dto.setCep(entity.getCep());
			dto.setRua(entity.getRua());
			dto.setNumero(entity.getNumero());
			dto.setComplemento(entity.getComplemento());
			dto.setBairro(entity.getBairro());
			dto.setCidade(entity.getCidade());
			dto.setEstado(entity.getEstado());
			dto.setCliente(entity.getCliente().getUserName());
			dto.setNome(entity.getNome());
			return dto;
	}
}
