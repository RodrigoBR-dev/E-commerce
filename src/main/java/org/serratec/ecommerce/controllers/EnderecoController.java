package org.serratec.ecommerce.controllers;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.serratec.ecommerce.dto.EnderecoDTOComp;
import org.serratec.ecommerce.dto.EnderecoDTONovo;
import org.serratec.ecommerce.dto.SedexDTO;
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
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

	@Autowired
	EnderecoService service;
	
	@GetMapping
	public SedexDTO getSedex() throws JAXBException {
		var restTemplate = new RestTemplate();
		String response = restTemplate.getForObject("http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?nCdEmpresa=08082650&sDsSenha=564321&sCepOrigem=70002900&sCepDestino=04547000&nVlPeso=1&nCdFormato=1&nVlComprimento=20&nVlAltura=20&nVlLargura=20&sCdMaoPropria=n&nVlValorDeclarado=0&sCdAvisoRecebimento=n&nCdServico=04510&nVlDiametro=0&StrRetorno=xml&nIndicaCalculo=3", String.class);
		StringReader reader = new StringReader(response);
		JAXBContext jaxbContext = JAXBContext.newInstance(SedexDTO.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		SedexDTO recordes = (SedexDTO) unmarshaller.unmarshal(reader);
		return recordes;
	}
	
	@GetMapping("/{cliente}")
	public ResponseEntity<List<EnderecoDTOComp>> getAll(@PathVariable String cliente) throws ClienteNotFoundException {
		return new ResponseEntity<>(service.getAll(cliente), HttpStatus.OK);
	}
	
	@GetMapping("/{cliente}/{nome}")
	public ResponseEntity<EnderecoDTOComp> findByNomeAndClienteDTO(@PathVariable String cliente, @PathVariable String nome) throws EnderecoNotFoundException, ClienteNotFoundException {
		return new ResponseEntity<>(service.findByNomeAndClienteDTO(nome, cliente), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<EnderecoDTOComp> create(@RequestBody EnderecoDTONovo dto) throws ViaCEPUnreachableException, ClienteNotFoundException {
		return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<EnderecoDTOComp> update(@RequestBody EnderecoDTONovo dto) throws EnderecoNotFoundException, ViaCEPUnreachableException, ClienteNotFoundException {
		return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
	}
	
	@DeleteMapping("/{cliente}/{nome}")
	public ResponseEntity<String> delete(@PathVariable String cliente, @PathVariable String nome) throws EnderecoNotFoundException, ClienteNotFoundException {
		return new ResponseEntity<>(service.delete(cliente, nome), HttpStatus.OK);
	}
}