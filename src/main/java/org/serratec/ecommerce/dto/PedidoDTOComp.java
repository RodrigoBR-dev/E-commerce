package org.serratec.ecommerce.dto;

import java.time.LocalDate;
import java.util.List;

import org.serratec.ecommerce.enums.StatusEnum;

public class PedidoDTOComp {

	private Long numeroDoPedido;

	private List<ProdutosPedidosDTO> produto;

	private Double totalProdutos;

	private String endEntrega;

	private Double frete;

	private Double valorTotalDoPedido;

	private LocalDate dataDoPedido;

	private LocalDate dataEntrega;

	private StatusEnum status;

	private ClienteDTO cliente;

	public Long getNumeroDoPedido() {
		return numeroDoPedido;
	}

	public void setNumeroDoPedido(Long numeroDoPedido) {
		this.numeroDoPedido = numeroDoPedido;
	}

	public List<ProdutosPedidosDTO> getProduto() {
		return produto;
	}

	public void setProduto(List<ProdutosPedidosDTO> produto) {
		this.produto = produto;
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

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO clienteDTO) {
		this.cliente = clienteDTO;
	}
}