package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.EnderecoEntradaDTO;
import org.serratec.ecommerce.dto.EnderecoRetornoDTO;
import org.serratec.ecommerce.dto.EnderecoViaCEPDTO;
import org.serratec.ecommerce.entities.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

	public EnderecoEntity enderecoViaDTOToEntity(EnderecoEntradaDTO enderecoDTO, EnderecoViaCEPDTO viaCEP) {
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
	
	public EnderecoRetornoDTO entityToEnderecoRetornoDTO(EnderecoEntity entity) {
			var dto = new EnderecoRetornoDTO();
			if (entity.getCliente() == null) return dto;
			dto.setCep(entity.getCep());
			dto.setRua(entity.getRua());
			dto.setNumero(entity.getNumero());
			dto.setComplemento(entity.getComplemento());
			dto.setBairro(entity.getBairro());
			dto.setCidade(entity.getCidade());
			dto.setEstado(entity.getEstado());
			dto.setCliente(entity.getCliente().getUserName());
			return dto;
	}
	

}
