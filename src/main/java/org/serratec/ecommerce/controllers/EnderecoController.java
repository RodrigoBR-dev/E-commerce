package org.serratec.ecommerce.controllers;

import java.util.List;

import org.serratec.ecommerce.dto.EnderecoDTOComp;
import org.serratec.ecommerce.dto.EnderecoDTONovo;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.exceptions.EnderecoNotFoundException;
import org.serratec.ecommerce.exceptions.NomeEnderecoExistenteException;
import org.serratec.ecommerce.exceptions.ViaCEPUnreachableException;
import org.serratec.ecommerce.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

	@Autowired
	EnderecoService service;
	
	@GetMapping("/{cliente}")
	public ResponseEntity<List<EnderecoDTOComp>> getAll(@PathVariable String cliente) throws ClienteNotFoundException {
		return new ResponseEntity<>(service.getAll(cliente), HttpStatus.OK);
	}
	
	@GetMapping("/{cliente}/{nome}")
	public ResponseEntity<EnderecoDTOComp> findByNomeAndClienteDTO(@PathVariable String cliente, @PathVariable String nome) throws EnderecoNotFoundException, ClienteNotFoundException {
		return new ResponseEntity<>(service.findByNomeAndClienteDTO(nome, cliente), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<EnderecoDTOComp> create(@RequestBody EnderecoDTONovo dto) throws ViaCEPUnreachableException, ClienteNotFoundException, NomeEnderecoExistenteException {
		return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<EnderecoDTOComp> update(@RequestBody EnderecoDTONovo dto) throws EnderecoNotFoundException, ViaCEPUnreachableException, ClienteNotFoundException, NomeEnderecoExistenteException {
		return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
	}
	
	@DeleteMapping("/{cliente}/{nome}")
	public ResponseEntity<String> delete(@PathVariable String cliente, @PathVariable String nome) throws EnderecoNotFoundException, ClienteNotFoundException {
		return new ResponseEntity<>(service.delete(cliente, nome), HttpStatus.OK);
	}
}