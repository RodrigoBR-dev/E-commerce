package org.serratec.ecommerce.dto;

import javax.validation.constraints.NotNull;

public class EnderecoDTONovo {

	@NotNull
	private String cep;
	
	@NotNull
	private String numero;
	
	private String complemento;
	
	@NotNull
	private String cliente;
	
	@NotNull
	private String nome;
	
	private String novoNome;

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

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNovoNome() {
		return novoNome;
	}

	public void setNovoNome(String novoNome) {
		this.novoNome = novoNome;
	}
}
