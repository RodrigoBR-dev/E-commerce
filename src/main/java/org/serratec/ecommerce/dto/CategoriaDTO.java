package org.serratec.ecommerce.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class CategoriaDTO {

	@NotNull
	private String nome;
	
	@JsonBackReference
	private String novoNome;
	
	private String descricao;
	private List<String> produtos = new ArrayList<>();

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<String> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<String> produtos) {
		this.produtos = produtos;
	}
}
