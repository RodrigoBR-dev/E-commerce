package org.serratec.ecommerce.enums;

public enum StatusEnum {
	RECEBIDO("Pedido recebido"),
	ENTREGUE("Pedido entregue"),
	PAGO("Pedido pago"),
	TRANSPORTE("Pedido em transporte");
	
	public String textoStatus;
	
	StatusEnum(String textoStatus) {
		this.textoStatus = textoStatus;
	}
	
}
