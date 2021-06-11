package org.serratec.ecommerce.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.dto.ClienteDTO;
import org.serratec.ecommerce.dto.ClienteDTONovo;
import org.serratec.ecommerce.entities.ClienteEntity;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.mapper.ClienteMapper;
import org.serratec.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;
	
	@Autowired
	ClienteMapper mapper;
	
	@Autowired
	EmailSenderService emailSender;
	
	public List<ClienteDTO> getAll() {
		List<ClienteEntity> listaCliente = repository.findAllByAtivoTrue();
		List<ClienteDTO> listaDTO = new ArrayList<>();
		for (ClienteEntity clienteEntity : listaCliente) {
			listaDTO.add(mapper.entityToDTO(clienteEntity));
		}
		return listaDTO;
	}
	
	
	public ClienteEntity findByUserNameOrEmail(String userNameOrEmail) throws ClienteNotFoundException { 
		Optional<ClienteEntity> cliente = repository.findByAtivoTrueAndUserNameOrEmail(userNameOrEmail, userNameOrEmail); 
		if (cliente.isPresent()) { 
			return cliente.get(); 
		}
		throw new ClienteNotFoundException("Cliente não encontrado!"); 
	}
	
	public ClienteDTO findByUserNameOrEmailDTO(String userNameOrEmail) throws ClienteNotFoundException { 
		Optional<ClienteEntity> cliente = repository.findByAtivoTrueAndUserNameOrEmail(userNameOrEmail, userNameOrEmail); 
		if (cliente.isPresent()) { 
			return mapper.entityToDTO(cliente.get());
		}
		throw new ClienteNotFoundException("Cliente não encontrado!"); 
	}

	public ClienteDTO create(ClienteDTONovo novoCliente) {
		ClienteEntity entity = mapper.clienteDTOnovoToEntity(novoCliente);		
		entity.setAtivo(true);
		emailSender.sendSimpleMessage(entity.getEmail(), "Bem vindo a nossa loja", "Olá " + entity.getNome() + ", seja bem vindo a nossa loja e boas compras!");
		return mapper.entityToDTO(repository.save(entity));
	}
	
	public ClienteDTO update(ClienteDTO novoCliente) throws ClienteNotFoundException {		
		ClienteEntity cliente = this.findByUserNameOrEmail(novoCliente.getUserName());
		cliente.setAtivo(true);
		if (novoCliente.getUserName() != null) {
			cliente.setUserName(novoCliente.getUserName());
		}
		
		if (novoCliente.getEmail() != null) {
			cliente.setEmail(novoCliente.getEmail());
		}
		if (novoCliente.getNome() != null) {
			cliente.setNome(novoCliente.getNome());
		}
		if (novoCliente.getTelefone() != null) {
			cliente.setTelefone(novoCliente.getTelefone());
		}
		if (novoCliente.getDataNascimento() != null) {
			cliente.setDataNascimento(novoCliente.getDataNascimento());
		}
		emailSender.sendSimpleMessage(cliente.getEmail(), "Dados alterados com sucesso", "Olá " + cliente.getNome() + ", seus dados foram alterados com sucesso!");
		return mapper.entityToDTO(repository.save(cliente));
	}
	
	public String delete(String userName) throws ClienteNotFoundException {
		ClienteEntity cliente = this.findByUserNameOrEmail(userName);
		cliente.setAtivo(false);
		repository.save(cliente);
		return "Cliente deletado com sucesso!";
	}
}
