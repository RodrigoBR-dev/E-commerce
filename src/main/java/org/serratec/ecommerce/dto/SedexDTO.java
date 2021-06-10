package org.serratec.ecommerce.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Servicos")
public class SedexDTO {

	private Double valor;
	private Integer prazoEntrega;

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getPrazoEntrega() {
		return prazoEntrega;
	}

	public void setPrazoEntrega(Integer prazoEntrega) {
		this.prazoEntrega = prazoEntrega;
	}
}
