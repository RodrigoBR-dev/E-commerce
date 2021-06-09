package org.serratec.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.dto.EnderecoDTONovo;
import org.serratec.ecommerce.dto.EnderecoDTOComp;
import org.serratec.ecommerce.dto.EnderecoViaCEPDTO;
import org.serratec.ecommerce.entities.ClienteEntity;
import org.serratec.ecommerce.entities.EnderecoEntity;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.exceptions.EnderecoNotFoundException;
import org.serratec.ecommerce.exceptions.ViaCEPUnreachableException;
import org.serratec.ecommerce.mapper.EnderecoMapper;
import org.serratec.ecommerce.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

	@Autowired
	EnderecoRepository repository;
	
	@Autowired
	EnderecoMapper mapper;
	
	@Autowired
	ClienteService clienteService;
	
	public List<EnderecoDTOComp> getAll(String cliente) throws ClienteNotFoundException {
		List<EnderecoEntity> listaEndereco = repository.findAllByCliente(clienteService.findByUserNameOrEmail(cliente));
		List<EnderecoDTOComp> listaDTO = new ArrayList<>();
		for (EnderecoEntity enderecoEntity : listaEndereco) {
			listaDTO.add(mapper.entityToEnderecoDTOComp(enderecoEntity));
		}
		return listaDTO;
	}
	
	public EnderecoEntity findByNomeAndCliente(String nome, ClienteEntity cliente) throws EnderecoNotFoundException {
		Optional<EnderecoEntity> endereco = repository.findByNomeAndCliente(nome, cliente);
		if (endereco.isPresent()) {
			return endereco.get();
		}
		throw new EnderecoNotFoundException("Endereço não encontrado!");
	}
	
	public EnderecoDTOComp findByNomeAndClienteDTO(String nome, String cliente) throws EnderecoNotFoundException, ClienteNotFoundException {
		EnderecoEntity endereco = this.findByNomeAndCliente(nome, clienteService.findByUserNameOrEmail(cliente));
		return mapper.entityToEnderecoDTOComp(endereco);
	}

	public EnderecoDTOComp create(EnderecoDTONovo enderecoDTO) throws ViaCEPUnreachableException, ClienteNotFoundException {
		ClienteEntity cliente = clienteService.findByUserNameOrEmail(enderecoDTO.getCliente());
		var viaCEP = this.getViaCEP(enderecoDTO.getCep());
		EnderecoEntity endereco = mapper.enderecoViaDTOToEntity(enderecoDTO, viaCEP);
		endereco.setCliente(cliente);
		return mapper.entityToEnderecoDTOComp(repository.save(endereco));
	}
	
	public EnderecoDTOComp update(EnderecoDTONovo novoEnd) throws ViaCEPUnreachableException, EnderecoNotFoundException, ClienteNotFoundException {
		EnderecoEntity endereco = this.findByNomeAndCliente(novoEnd.getNome(), clienteService.findByUserNameOrEmail(novoEnd.getCliente()));
		if (novoEnd.getCep() != null) {
			endereco.setCep(novoEnd.getCep());
			var viaCEP = this.getViaCEP(novoEnd.getCep());
			endereco.setRua(viaCEP.getLogradouro());
			endereco.setBairro(viaCEP.getBairro());
			endereco.setCidade(viaCEP.getLocalidade());
			endereco.setEstado(viaCEP.getUf());
		}
		if (novoEnd.getNumero() != null) {
			endereco.setNumero(novoEnd.getNumero());
		}
		if (novoEnd.getComplemento() != null) {
			endereco.setComplemento(novoEnd.getComplemento());
		}
		return mapper.entityToEnderecoDTOComp(repository.save(endereco));
	}
	
	public String delete(String cliente, String nome) throws EnderecoNotFoundException, ClienteNotFoundException {
		EnderecoEntity endereco = this.findByNomeAndCliente(nome, clienteService.findByUserNameOrEmail(cliente));
		repository.delete(endereco);
		return "Endereço deletado com sucesso!";
	}
	
	public EnderecoViaCEPDTO getViaCEP(String cep) throws ViaCEPUnreachableException {
		var restTemplate = new RestTemplate();
		Optional<EnderecoViaCEPDTO> viaCEP = Optional.ofNullable(restTemplate.getForObject("http://viacep.com.br/ws/" + cep + "/json/", EnderecoViaCEPDTO.class));
		if (viaCEP.isPresent()) return viaCEP.get();
		throw new ViaCEPUnreachableException("CEP não encontrado.");
	}
}
