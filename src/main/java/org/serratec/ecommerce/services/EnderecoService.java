package org.serratec.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.dto.EnderecoDTO;
import org.serratec.ecommerce.dto.EnderecoViaCEPDTO;
import org.serratec.ecommerce.entities.EnderecoEntity;
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
	
	public List<EnderecoEntity> getAll() {
		return repository.findAll();
	}
	
	public EnderecoEntity findById(Long id) throws EnderecoNotFoundException {
		Optional<EnderecoEntity> endereco = repository.findById(id);
		if (endereco.isPresent()) {
			return endereco.get();
		}
		throw new EnderecoNotFoundException("Endereço não encontrado!");
	}
	
	public EnderecoEntity create(EnderecoDTO enderecoDTO) throws ViaCEPUnreachableException {
		var viaCEP = this.getViaCEP(enderecoDTO.getCep());
		EnderecoEntity endereco = EnderecoMapper.enderecoViaDTOToEntity(enderecoDTO, viaCEP);
		endereco.setAtivo(true);
		return repository.save(endereco);
	}
	
	public EnderecoEntity update(EnderecoEntity novoEnd) throws EnderecoNotFoundException, ViaCEPUnreachableException {
		EnderecoEntity endereco = this.findById(novoEnd.getId());
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
		return repository.save(endereco);
	}
	
	public String delete(Long id) throws EnderecoNotFoundException {
		EnderecoEntity endereco = this.findById(id);
		endereco.setAtivo(false);
		repository.save(endereco);
		return "Endereço deletado com sucesso!";
	}
	
	public EnderecoViaCEPDTO getViaCEP(String cep) throws ViaCEPUnreachableException {
		var restTemplate = new RestTemplate();
		var viaCEP = restTemplate.getForObject("http://viacep.com.br/ws/" + cep + "/json/", EnderecoViaCEPDTO.class);
		if (viaCEP.getCep() != null) return viaCEP;
		throw new ViaCEPUnreachableException("CEP não encontrado.");
	}
}
