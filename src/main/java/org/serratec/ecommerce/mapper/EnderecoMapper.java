package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.EnderecoDTO;
import org.serratec.ecommerce.dto.EnderecoViaCEPDTO;
import org.serratec.ecommerce.entities.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

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
