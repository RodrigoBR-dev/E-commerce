package org.serratec.ecommerce.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.serratec.ecommerce.dto.EnderecoDTONovo;
import org.serratec.ecommerce.dto.EnderecoDTOComp;
import org.serratec.ecommerce.entities.EnderecoEntity;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.exceptions.EnderecoNotFoundException;
import org.serratec.ecommerce.exceptions.ViaCEPUnreachableException;
import org.serratec.ecommerce.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

	@Autowired
	EnderecoService service;
	
	@GetMapping
	public ResponseEntity<List<EnderecoDTOComp>> getAll() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<EnderecoDTOComp> findByNomeAndClienteDTO(@PathParam(value = "nome") String nome, String cliente) throws EnderecoNotFoundException {
		return new ResponseEntity<>(service.findByNomeAndClienteDTO(nome, cliente), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<EnderecoDTOComp> create(@RequestBody EnderecoDTONovo dto) throws ViaCEPUnreachableException, ClienteNotFoundException {
		return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<EnderecoDTOComp> update(@RequestBody EnderecoDTONovo dto) throws EnderecoNotFoundException, ViaCEPUnreachableException {
		return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<String> delete(@PathParam(value = "nome") String nome, String cliente) throws EnderecoNotFoundException {
		return new ResponseEntity<>(service.delete(nome, cliente), HttpStatus.OK);
	}
}