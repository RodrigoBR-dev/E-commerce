package org.serratec.ecommerce.enums;

public enum StatusEnum {
	RECEBIDO("Pedido recebido"),
	FECHADO("Pedido fechado"),
	PAGO("Pedido pago"),
	TRANSPORTE("Pedido em transporte"),
	ENTREGUE("Pedido entregue"),
	CANCELADO("Pedido cancelado");
	
	StatusEnum(String string) {
	}
}
