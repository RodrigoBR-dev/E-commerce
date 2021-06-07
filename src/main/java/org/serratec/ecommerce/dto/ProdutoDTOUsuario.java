package org.serratec.ecommerce.dto;

import java.time.LocalDate;

public class ProdutoDTOUsuario {
	private String nome;
	private String descricao;
	private Double preco;
	private LocalDate dataCadastro;
	private Integer quantEstoque;
	private String imagem;
	
	
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
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
}
