package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.entities.PedidoEntity;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {
	public PedidoEntity toEntity(PedidoDTO dto) {
		PedidoEntity entity = new PedidoEntity();
		entity.setNumeroDoPedido(dto.getNumeroDoPedido());
		entity.setValorTotalDoPedido(dto.getValorTotalDoPedido());
		entity.setDataDoPedido(dto.getDataDoPedido());
		
		return entity;
	}
	
	public PedidoDTO toDTO(PedidoEntity entity) {
		PedidoDTO dto = new PedidoDTO();
		dto.setNumeroDoPedido(entity.getNumeroDoPedido());
		dto.setValorTotalDoPedido(entity.getValorTotalDoPedido());
		dto.setDataDoPedido(entity.getDataDoPedido());
		
		return dto;
	}
}
