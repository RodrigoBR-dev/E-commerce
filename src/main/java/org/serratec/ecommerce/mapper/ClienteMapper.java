package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.ClienteDTO;
import org.serratec.ecommerce.dto.ClienteDTONovo;
import org.serratec.ecommerce.entities.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

	@Autowired
	BCryptPasswordEncoder bCrypt;
	
	public ClienteDTO entityToDTO(ClienteEntity entity) {
		var dto = new ClienteDTO();
		dto.setUserName(entity.getUserName());
		dto.setEmail(entity.getEmail());
		dto.setNome(entity.getNome());
		dto.setCpf(entity.getCpf());
		dto.setTelefone(entity.getTelefone());
		dto.setDataNascimento(entity.getDataNascimento());
		return dto;
	}
	
	public ClienteEntity clienteDTOToEntity(ClienteDTO dto) {
		var entity = new ClienteEntity();
		entity.setUserName(dto.getUserName());
		entity.setEmail(dto.getEmail());
		entity.setNome(dto.getNome());
		entity.setCpf(dto.getCpf());
		entity.setTelefone(dto.getTelefone());
		entity.setDataNascimento(dto.getDataNascimento());
		return entity;
	}
	
	public ClienteEntity clienteDTOnovoToEntity(ClienteDTONovo dto) {
		var entity = new ClienteEntity();
		entity.setEmail(dto.getEmail());
		entity.setSenha(bCrypt.encode(dto.getSenha()));
		entity.setUserName(dto.getUserName());
		entity.setNome(dto.getNome());
		entity.setCpf(dto.getCpf());
		entity.setTelefone(dto.getTelefone());
		entity.setDataNascimento(dto.getDataNascimento());
		return entity;
	}
}
