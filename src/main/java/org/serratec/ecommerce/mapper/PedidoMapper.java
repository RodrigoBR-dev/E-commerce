package org.serratec.ecommerce.mapper;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.dto.PedidoDTOAll;
import org.serratec.ecommerce.dto.PedidoDTOComp;
import org.serratec.ecommerce.entities.PedidoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {
	
	@Autowired
	ClienteMapper mapper;
	
	public PedidoEntity toEntity(PedidoDTO dto) {
		var entity = new PedidoEntity();
		var numero = Math.random();
		while (numero < 10000000) {
			numero = numero * 10;
		}
		entity.setNumeroDoPedido((long) numero);
		entity.setEndEntrega(dto.getEndEntrega());
		return entity;
	}
	
	public PedidoDTOAll entityToAll(PedidoEntity entity) {
		var dto = new PedidoDTOAll();
		dto.setNumeroDoPedido(entity.getNumeroDoPedido());
		dto.setValorTotalDoPedido(entity.getValorTotalDoPedido());
		dto.setDataDoPedido(entity.getDataDoPedido());
		dto.setDataEntrega(entity.getDataEntrega());
		dto.setStatus(entity.getStatus());
		return dto;
	}

	public PedidoDTOComp entityToDTOComp(PedidoEntity entity) {
		var dto = new PedidoDTOComp();
		dto.setNumeroDoPedido(entity.getNumeroDoPedido());
		dto.setEndEntrega(entity.getEndEntrega());
		dto.setFrete(entity.getFrete());
		dto.setTotalProdutos(entity.getTotalProdutos());
		dto.setValorTotalDoPedido(entity.getValorTotalDoPedido());
		dto.setDataDoPedido(entity.getDataDoPedido());
		dto.setDataEntrega(entity.getDataEntrega());
		dto.setStatus(entity.getStatus());
		dto.setCliente(mapper.entityToDTO(entity.getCliente()));
		return dto;
	}
	
	
}