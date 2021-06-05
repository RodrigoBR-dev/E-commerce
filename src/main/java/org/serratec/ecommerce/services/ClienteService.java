package org.serratec.ecommerce.services;


import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.ClienteEntity;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;
	
	public List<ClienteEntity> getAll() {
		return repository.findAll();
	}
	
	public ClienteEntity findById(Long id) throws ClienteNotFoundException {
		Optional<ClienteEntity> cliente = repository.findById(id);
		if (cliente.isPresent()) {
			return cliente.get();
		}
		throw new ClienteNotFoundException("Cliente não encontrado!");
	}
	
	public ClienteEntity create(ClienteEntity novoCliente) {
		novoCliente.setAtivo(true);
		return repository.save(novoCliente);
	}
	
	public ClienteEntity update(ClienteEntity novoCliente) throws ClienteNotFoundException {
		ClienteEntity cliente = this.findById(novoCliente.getId());
		if (novoCliente.getEmail() != null) {
			cliente.setEmail(novoCliente.getEmail());
		}
		if (novoCliente.getUserName() != null) {
			cliente.setUserName(novoCliente.getUserName());
		}
		if (novoCliente.getSenha() != null) {
			cliente.setSenha(novoCliente.getSenha());
		}
		if (novoCliente.getNome() != null) {
			cliente.setNome(novoCliente.getNome());
		}
		if (novoCliente.getCpf() != null) {
			cliente.setCpf(novoCliente.getCpf());
		}
		if (novoCliente.getTelefone() != null) {
			cliente.setTelefone(novoCliente.getTelefone());
		}
		if (novoCliente.getDataNascimento() != null) {
			cliente.setDataNascimento(novoCliente.getDataNascimento());
		}
		return repository.save(cliente);
	}
	
	public String delete(Long id) throws ClienteNotFoundException {
		ClienteEntity cliente = this.findById(id);
		cliente.setAtivo(false);
		System.out.println(cliente.isAtivo());
		repository.save(cliente);
		return "Cliente deletado com sucesso!";
	}
}
