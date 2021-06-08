package org.serratec.ecommerce.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.entities.PedidoEntity;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.entities.ProdutosPedidosEntity;
import org.serratec.ecommerce.enums.StatusEnum;
import org.serratec.ecommerce.exceptions.EstoqueInsuficienteException;
import org.serratec.ecommerce.exceptions.PedidoFinalizadoException;
import org.serratec.ecommerce.exceptions.PedidoNotFoundException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.exceptions.QuantityException;
import org.serratec.ecommerce.exceptions.ValorNegativoException;
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
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	ProdutosPedidosService produtosPedidosService;
	
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
	
	public String create(PedidoDTO pedidoNovo) throws ProdutoNotFoundException {
		PedidoEntity pedido = mapper.toEntity(pedidoNovo);
		pedido.setDataDoPedido(LocalDate.now());
		pedido.setStatus(StatusEnum.RECEBIDO);
		pedido = repository.save(pedido);
		produtosPedidosService.create(pedido, pedidoNovo);
		return "Criado com sucesso";
	}
	
	public PedidoDTO acrescentarProduto(PedidoDTO pedido) throws PedidoNotFoundException, ProdutoNotFoundException, EstoqueInsuficienteException, ValorNegativoException, QuantityException {
		PedidoEntity pedidoEntity = getByNumero(pedido.getNumeroDoPedido());
		ProdutoEntity produtoEntity = produtoService.findByNome(pedido.getProduto());
		Optional<ProdutosPedidosEntity> produtosPedidos = Optional.ofNullable(produtosPedidosService.findByPedidoAndProduto(pedidoEntity, produtoEntity));
		if (produtosPedidos.isPresent()) {
			ProdutosPedidosEntity produtosPedidosEntity  = produtosPedidos.get();
			if(produtosPedidosEntity.getQuantidade() < pedido.getQuantidade()) {
				produtosPedidosEntity.setQuantidade(pedido.getQuantidade());
				Integer quantidade = produtosPedidosEntity.getQuantidade() - pedido.getQuantidade();
				produtoService.vender(pedido.getProduto(), quantidade);
			} else {
				throw new QuantityException("A quantidade nova eh inferior a quantidade existente!");
			}
		}
		return pedido;
	}
	
	public String pagamento(Long numeroDoPedido) throws PedidoNotFoundException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		pedido.setDataDoPedido(LocalDate.now());
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
