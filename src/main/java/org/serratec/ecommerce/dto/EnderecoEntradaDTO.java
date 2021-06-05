package org.serratec.ecommerce.dto;

import javax.validation.constraints.NotNull;

public class EnderecoEntradaDTO {

	@NotNull
	private String cep;
	
	@NotNull
	private String numero;
	
	private String complemento;
	
	@NotNull
	private Long cliente;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}
}
