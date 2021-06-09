package org.serratec.ecommerce.dto;

public class SedexDTO {

	private Double Valor;
	private Integer PrazoEntrega;

	public Double getValor() {
		return Valor;
	}

	public void setValor(Double valor) {
		this.Valor = valor;
	}

	public Integer getPrazoEntrega() {
		return PrazoEntrega;
	}

	public void setPrazoEntrega(Integer prazoEntrega) {
		this.PrazoEntrega = prazoEntrega;
	}
}
