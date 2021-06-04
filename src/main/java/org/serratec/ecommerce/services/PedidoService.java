package org.serratec.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.PedidoEntity;
import org.serratec.ecommerce.exceptions.PedidoNotFoundException;
import org.serratec.ecommerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository repository;
	
	public List<PedidoEntity> getAll() {
		return repository.findAll(Sort.by("numeroDoPedido"));
	}
	
	public PedidoEntity getById(Long id) throws PedidoNotFoundException {
		Optional<PedidoEntity> pedido = repository.findById(id);
		if (pedido.isEmpty()) {
			throw new PedidoNotFoundException("Não existe pedido com esse Id.");
		}
		return pedido.get();
	}
	
	public void create(PedidoEntity pedido) {
		pedido.setAtivo(true);
		repository.save(pedido);
	}
	
	public PedidoEntity update(Long id, PedidoEntity pedido) throws PedidoNotFoundException {
		PedidoEntity pedidoEntity = getById(id);
		if (pedido.getNumeroDoPedido() != null) {
			pedidoEntity.setNumeroDoPedido(pedido.getNumeroDoPedido());
		}
//		if (pedido.getListaDeProdutos() != null) {
//			pedidoEntity.setListaDeProdutos(pedido.getListaDeProdutos());
//		}
		if (pedido.getValorTotalDoPedido() != null) {
			pedidoEntity.setValorTotalDoPedido(pedido.getValorTotalDoPedido());
		}
		if (pedido.getDataDoPedido() != null) {
			pedidoEntity.setDataDoPedido(pedido.getDataDoPedido());
		}
		if (pedido.getStatus() != null) {
			pedidoEntity.setStatus(pedido.getStatus());
		}
		return repository.save(pedidoEntity);
	}
	
	public String delete(Long id) throws PedidoNotFoundException {
		PedidoEntity pedido = this.getById(id);
		pedido.setAtivo(false);
		repository.deleteById(id);
		return "Pedido deletado com sucesso!";
	}
	
}
