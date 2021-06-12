package org.serratec.ecommerce.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.dto.ClienteDTO;
import org.serratec.ecommerce.dto.ClienteDTONovo;
import org.serratec.ecommerce.entities.ClienteEntity;
import org.serratec.ecommerce.exceptions.AtributoEncontradoException;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.mapper.ClienteMapper;
import org.serratec.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;
	
	@Autowired
	ClienteMapper mapper;
	
	@Autowired
	BCryptPasswordEncoder bCrypt;
	
	public List<ClienteDTO> getAll() {
		List<ClienteEntity> listaCliente = repository.findAllByAtivoTrue();
		List<ClienteDTO> listaDTO = new ArrayList<>();
		for (ClienteEntity clienteEntity : listaCliente) {
			listaDTO.add(mapper.entityToDTO(clienteEntity));
		}
		return listaDTO;
	}
	
	public ClienteEntity findByUserNameOrEmail(String userNameOrEmail) throws ClienteNotFoundException { 
		Optional<ClienteEntity> cliente = repository.findByAtivoTrueAndUserNameOrEmailOrCpf(userNameOrEmail, userNameOrEmail, userNameOrEmail); 
		if (cliente.isPresent()) { 
			return cliente.get(); 
		}
		throw new ClienteNotFoundException("Cliente não encontrado!"); 
	}
	
	public ClienteDTO findByUserNameOrEmailDTO(String userNameOrEmail) throws ClienteNotFoundException { 
		Optional<ClienteEntity> cliente = repository.findByAtivoTrueAndUserNameOrEmailOrCpf(userNameOrEmail, userNameOrEmail, userNameOrEmail); 
		if (cliente.isPresent()) { 
			return mapper.entityToDTO(cliente.get());
		}
		throw new ClienteNotFoundException("Cliente não encontrado!"); 
	}

	public ClienteDTO create(ClienteDTONovo novoCliente) throws AtributoEncontradoException {
		ClienteEntity entity = mapper.clienteDTOnovoToEntity(novoCliente);	
		Optional<ClienteEntity> userName = repository.findByUserNameOrEmailOrCpf(entity.getUserName(), entity.getUserName(), entity.getUserName());
		Optional<ClienteEntity> email = repository.findByUserNameOrEmailOrCpf(entity.getEmail(), entity.getEmail(), entity.getEmail());
		Optional<ClienteEntity> cpf = repository.findByUserNameOrEmailOrCpf(entity.getCpf(), entity.getCpf(), entity.getCpf());
		if (userName.isPresent()) throw new AtributoEncontradoException("UserName já utilizado!");
		if (email.isPresent()) throw new AtributoEncontradoException("Email já cadastrado!");
		if (cpf.isPresent()) throw new AtributoEncontradoException("CPF já cadastrado!");
		entity.setAtivo(true);
		return mapper.entityToDTO(repository.save(this.hash(entity)));
	}
	
	public ClienteDTO update(ClienteDTO novoCliente) throws ClienteNotFoundException {		
		ClienteEntity cliente = this.findByUserNameOrEmail(novoCliente.getCpf());
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
		return mapper.entityToDTO(repository.save(this.hash(cliente)));
	}
	
	public String reativaCliente(String cpf) throws ClienteNotFoundException {
		ClienteEntity cliente = this.findByUserNameOrEmail(cpf);
		cliente.setAtivo(true);
		repository.save(cliente);
		return "Cliente reativado!";
	}
	
	public String delete(String userName) throws ClienteNotFoundException {
		ClienteEntity cliente = this.findByUserNameOrEmail(userName);
		cliente.setAtivo(false);
		repository.save(cliente);
		return "Cliente deletado com sucesso!";
	}
	
	private ClienteEntity hash(ClienteEntity cliente) {
		cliente.setToken(bCrypt.encode(cliente.getEmail() + cliente.getCpf() + cliente.getDataNascimento()));
		return cliente;
	}
}