package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.ClienteDTO;
import org.serratec.ecommerce.entities.ClienteEntity;

public class ClienteMapper {

	public ClienteDTO entityToDTO(ClienteEntity entity) {
		var dto = new ClienteDTO();
		dto.setUserName(entity.getUserName());
		dto.setNome(entity.getNome());
		dto.setCpf(entity.getCpf());
		dto.setTelefone(entity.getTelefone());
		dto.setDataNascimento(entity.getDataNascimento());
		return dto;
	}
	
	public ClienteEntity DTOToEntity(ClienteDTO dto) {
		var entity = new ClienteEntity();
		entity.setUserName(dto.getUserName());
		entity.setNome(dto.getNome());
		entity.setCpf(dto.getCpf());
		entity.setTelefone(dto.getTelefone());
		entity.setDataNascimento(dto.getDataNascimento());
		return entity;
	}
	
}
