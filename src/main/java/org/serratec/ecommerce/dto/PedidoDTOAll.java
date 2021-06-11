package org.serratec.ecommerce.dto;

import java.time.LocalDate;

import org.serratec.ecommerce.enums.StatusEnum;

public class PedidoDTOAll {
	
	private Long numeroDoPedido;
	
    private Double valorTotalDoPedido;
    
    private LocalDate dataDoPedido;
    
    private LocalDate dataEntrega;
    
    private StatusEnum status;

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

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}   
}