package org.serratec.ecommerce.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Servicos")
public class SedexDTO {

	@XmlElement(name = "Valor")
	public Double valor;
	
	@XmlElement(name = "PrazoEntrega")
	public Integer prazoEntrega;

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
