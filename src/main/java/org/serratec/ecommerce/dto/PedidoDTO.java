package org.serratec.ecommerce.dto;

public class PedidoDTO {

	private Long numeroDoPedido;

	private String produto;

	private String endEntrega;

	private String cliente;
	
	private Integer quantidade;

	public Long getNumeroDoPedido() {
		return numeroDoPedido;
	}

	public void setNumeroDoPedido(Long numeroDoPedido) {
		this.numeroDoPedido = numeroDoPedido;
	}

	public String getEndEntrega() {
		return endEntrega;
	}

	public void setEndEntrega(String endEntrega) {
		this.endEntrega = endEntrega;
	}


	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}
	
	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}