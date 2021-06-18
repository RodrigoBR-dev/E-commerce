package org.serratec.ecommerce.controllers;

import java.util.List;

import javax.mail.MessagingException;

import org.serratec.ecommerce.dto.ClienteDTO;
import org.serratec.ecommerce.dto.ClienteDTONovo;
import org.serratec.ecommerce.exceptions.AtributoEncontradoException;
import org.serratec.ecommerce.exceptions.ClienteNotFoundException;
import org.serratec.ecommerce.exceptions.InvalidTokenException;
import org.serratec.ecommerce.services.ClienteService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	ClienteService service;

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> getAll() {
		return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{userNameOrEmail}")
	public ResponseEntity<ClienteDTO> findByUserNameOrEmail(@PathVariable String userNameOrEmail) throws ClienteNotFoundException {
		return new ResponseEntity<>(service.findByUserNameOrEmailDTO(userNameOrEmail), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTONovo entity) throws AtributoEncontradoException, MessagingException {
		return new ResponseEntity<>(service.create(entity), HttpStatus.CREATED);
	}
	
	@PostMapping("/recupera/{cpf}")
	public ResponseEntity<String> recupera(@PathVariable String cpf) throws ClienteNotFoundException, MessagingException {
		return new ResponseEntity<>(service.recuperarSenha(cpf), HttpStatus.OK);
	}
	
	@PutMapping("/recupera")
	public ResponseEntity<String> recuperarSenha(@RequestParam("token") String token, @RequestBody ClienteDTONovo entity) throws ClienteNotFoundException, InvalidTokenException {
		return new ResponseEntity<>(service.updateSenha(token, entity), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<ClienteDTO> update(@RequestBody ClienteDTO entity) throws ClienteNotFoundException, MessagingException {
		return new ResponseEntity<>(service.update(entity), HttpStatus.OK);
	}
	
	@PutMapping("/{cpf}")
	public ResponseEntity<String> reativaCliente(@PathVariable String cpf) throws ClienteNotFoundException {
		return new ResponseEntity<>(service.reativaCliente(cpf), HttpStatus.OK);
	}

	@DeleteMapping("/{userName}")
	public ResponseEntity<String> delete(@PathVariable String userName) throws ClienteNotFoundException {
		return new ResponseEntity<>(service.delete(userName), HttpStatus.OK);
	}
}
