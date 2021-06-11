package org.serratec.ecommerce.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ProdutoDTOUsuario {
	private String nome;
	private String descricao;
	private Double preco;
	private LocalDate dataCadastro;
	private Integer quantEstoque;
	private String url;
	private String categoria;
	@JsonBackReference
	private String novoNome;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Integer getQuantEstoque() {
		return quantEstoque;
	}
	public void setQuantEstoque(Integer quantEstoque) {
		this.quantEstoque = quantEstoque;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String imagem) {
		this.url = imagem;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getNovoNome() {
		return novoNome;
	}
	public void setNovoNome(String novoNome) {
		this.novoNome = novoNome;
	}
	
}