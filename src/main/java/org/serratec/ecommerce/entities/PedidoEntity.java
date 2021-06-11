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

import org.serratec.ecommerce.enums.StatusEnum;

@Entity
@Table(name = "Pedido")
public class PedidoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long numeroDoPedido;
	
	private Double totalProdutos;
	
	private String endEntrega;
	
	private Double frete;
	
	private Double valorTotalDoPedido;
	
	private LocalDate dataDoPedido;
	
	@OneToMany(mappedBy = "pedido")
	private List<ProdutosPedidosEntity> produtosPedidos = new ArrayList<>();
	
	
	private LocalDate dataEntrega;
	
	
	private StatusEnum status;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private ClienteEntity cliente;
	
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

	public Double getTotalProdutos() {
		return totalProdutos;
	}

	public void setTotalProdutos(Double totalProdutos) {
		this.totalProdutos = totalProdutos;
	}

	public String getEndEntrega() {
		return endEntrega;
	}

	public void setEndEntrega(String endEntrega) {
		this.endEntrega = endEntrega;
	}

	public Double getFrete() {
		return frete;
	}

	public void setFrete(Double frete) {
		this.frete = frete;
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

	public List<ProdutosPedidosEntity> getProdutos() {
		return produtosPedidos;
	}

	public void setProdutos(List<ProdutosPedidosEntity> produtosPedidos) {
		this.produtosPedidos = produtosPedidos;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

}
