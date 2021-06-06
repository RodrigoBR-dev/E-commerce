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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Pedido")
public class PedidoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long numeroDoPedido;

	@NotNull
	private Double valorTotalDoPedido;
	
	@NotNull
	private LocalDate dataDoPedido;
	
	@OneToMany(mappedBy = "pedido")
	private List<ProdutosPedidos> produtosPedidos = new ArrayList<>();

	private LocalDate dataEntrega;
	
	@NotNull
	private String status;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private ClienteEntity cliente;
	
//	@OneToMany(mappedBy = "pedido")
//	private List<ProdutoEntity> produtos = new ArrayList<>();
	
	private Boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroDoPedido() {
		return numeroDoPedido;
	}

	public void setNumeroDoPedido(Long numeroDoPedido) {
		this.numeroDoPedido = numeroDoPedido;
	}

	public Double getValorTotalDoPedido() {
		return valorTotalDoPedido;
	}

	public void setValorTotalDoPedido(Double valorTotalDoPedido) {
		this.valorTotalDoPedido = valorTotalDoPedido;
	}

	public LocalDate getDataDoPedido() {
		return dataDoPedido;
	}

	public void setDataDoPedido(LocalDate dataDoPedido) {
		this.dataDoPedido = dataDoPedido;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}

//	public List<ProdutoEntity> getProdutos() {
//		return produtos;
//	}
//
//	public void setProdutos(List<ProdutoEntity> produtos) {
//		this.produtos = produtos;
//	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
