package org.serratec.ecommerce.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.serratec.ecommerce.exceptions.ValorNegativoException;

@Entity
@Table(name = "produto")
public class ProdutoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private Double preco;
	private Integer quantEstoque;
	private LocalDate dataCadastro;
	private String imagem;
	
	@OneToMany
	private List<ProdutosPedidos> produtosPedidos = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private CategoriaEntity categoria;
	
//	@OneToMany(mappedBy = "produtos")
//	private List<PedidoEntity> pedidos = new ArrayList<>();

	private Boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public void setPreco(Double preco) throws ValorNegativoException {
		if(preco < 0) {
			throw new ValorNegativoException("Não deverá informar um valor negativo para estoque! ");
		}
		this.preco = preco;
	}

	public Integer getQuantEstoque() {
		return quantEstoque;
	}

	public void setQuantEstoque(Integer quantEstoque) throws ValorNegativoException {
		if(quantEstoque < 0) {
			throw new ValorNegativoException("Não deverá informar um valor negativo para estoque!");
		}
		this.quantEstoque = quantEstoque;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public CategoriaEntity getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEntity categoria) {
		this.categoria = categoria;
	}

	// Exclusão lógica
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

//	public List<PedidoEntity> getPedidos() {
//		return pedidos;
//	}
//
//	public void setPedidos(List<PedidoEntity> pedidos) {
//		this.pedidos = pedidos;
//	}
}
