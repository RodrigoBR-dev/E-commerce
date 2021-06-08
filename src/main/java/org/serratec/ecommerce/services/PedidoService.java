package org.serratec.ecommerce.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.entities.PedidoEntity;
import org.serratec.ecommerce.enums.StatusEnum;
import org.serratec.ecommerce.exceptions.PedidoFinalizadoException;
import org.serratec.ecommerce.exceptions.PedidoNotFoundException;
import org.serratec.ecommerce.mapper.PedidoMapper;
import org.serratec.ecommerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository repository;
	
	@Autowired
	PedidoMapper mapper;
	
	public List<PedidoDTO> getAll() {
		return repository.findAll(Sort.by("dataDoPedido")).stream().map(mapper::toDTO).collect(Collectors.toList());
	}
	
	public PedidoEntity getByNumero(Long numeroDoPedido) throws PedidoNotFoundException {
		Optional<PedidoEntity> pedido = repository.findById(numeroDoPedido);
		if (pedido.isEmpty()) {
			throw new PedidoNotFoundException("Não existe pedido com esse numero.");
		}
		return pedido.get();
	}
	
	public String create(PedidoDTO pedidoNovo) {
		PedidoEntity pedido = mapper.toEntity(pedidoNovo);
		pedido.setDataDoPedido(LocalDate.now());
		pedido.setStatus(StatusEnum.RECEBIDO);
		repository.save(pedido);
		return "Criado com sucesso";
	}
	
	public PedidoEntity update(Long numeroDoPedido, PedidoEntity pedido) throws PedidoNotFoundException {
		PedidoEntity pedidoEntity = getByNumero(numeroDoPedido);
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
	
	public String pagamento(Long numeroDoPedido) throws PedidoNotFoundException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		pedido.setStatus(StatusEnum.PAGO);
		repository.save(pedido);
		return "Pagamento Recebido!";
	}
	
	public String transporte(Long numeroDoPedido) throws PedidoNotFoundException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		pedido.setStatus(StatusEnum.TRANSPORTE);
		repository.save(pedido);
		return "Pedido em transporte!";
	}
	
	public String entrega(Long numeroDoPedido) throws PedidoNotFoundException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		pedido.setStatus(StatusEnum.ENTREGUE);
		repository.save(pedido);
		return "Pedido entregue!";
	}
	
	public String delete(Long numeroDoPedido) throws PedidoNotFoundException, PedidoFinalizadoException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		if(pedido.getStatus() == StatusEnum.ENTREGUE) throw new PedidoFinalizadoException("Pedidos finalizados nao podem ser deletados"); 
		repository.delete(pedido);
		return "Pedido deletado com sucesso!";
	}
	
}
