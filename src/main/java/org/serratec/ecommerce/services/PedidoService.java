package org.serratec.ecommerce.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.dto.PedidoDTOAll;
import org.serratec.ecommerce.dto.PedidoDTOComp;
import org.serratec.ecommerce.dto.ProdutosPedidosDTO;
import org.serratec.ecommerce.entities.PedidoEntity;
import org.serratec.ecommerce.entities.ProdutosPedidosEntity;
import org.serratec.ecommerce.enums.StatusEnum;
import org.serratec.ecommerce.exceptions.EstoqueInsuficienteException;
import org.serratec.ecommerce.exceptions.NotclosedPedidoException;
import org.serratec.ecommerce.exceptions.PedidoFinalizadoException;
import org.serratec.ecommerce.exceptions.PedidoNotFoundException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
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
	
	public List<PedidoDTOAll> getAll() {
		return repository.findAll(Sort.by("dataDoPedido")).stream().map(mapper::EntityToAll).collect(Collectors.toList());
	}
	
	public PedidoDTOComp getByNumeroDTO(Long numeroDoPedido) throws PedidoNotFoundException {
		Optional<PedidoEntity> pedido = repository.findByNumeroDoPedido(numeroDoPedido);
		if (pedido.isEmpty()) {
			throw new PedidoNotFoundException("Não existe pedido com esse numero.");
		}
		PedidoDTOComp comp = mapper.EntityToDTOComp(pedido.get());
		
		ArrayList<ProdutosPedidosDTO> listaProduto = new ArrayList<>();
		
		List<ProdutosPedidosEntity> produtosPedidos = produtosPedidosService.findByPedido(pedido.get());
		for (ProdutosPedidosEntity produtosPedidosEntity : produtosPedidos) {
			ProdutosPedidosDTO produtosPedidosDTO = new ProdutosPedidosDTO();
			produtosPedidosDTO.setNome(produtosPedidosEntity.getProduto().getNome());
			produtosPedidosDTO.setImagem(produtosPedidosEntity.getProduto().getImagem());
			produtosPedidosDTO.setValor(produtosPedidosEntity.getPreco());
			produtosPedidosDTO.setQuantidade(produtosPedidosEntity.getQuantidade());
			
			listaProduto.add(produtosPedidosDTO);
		}
		comp.setProduto(listaProduto);
		return comp;
	}
	
	public PedidoEntity getByNumero(Long numeroDoPedido) throws PedidoNotFoundException {
		Optional<PedidoEntity> pedido = repository.findByNumeroDoPedido(numeroDoPedido);
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
		var produtosPedidos = produtosPedidosService.create(pedido, pedidoNovo);
		pedido.setValorTotalDoPedido(produtosPedidos.getPreco() * produtosPedidos.getQuantidade());
		repository.save(pedido);
		return "Criado com sucesso";
	}
	
	public PedidoDTO update(PedidoDTO pedido) throws PedidoNotFoundException, ProdutoNotFoundException, EstoqueInsuficienteException {
		var pedidoEntity = getByNumero(pedido.getNumeroDoPedido());
		var produtoEntity = produtoService.findByNome(pedido.getProduto());
		Optional<ProdutosPedidosEntity> produtosPedidos = Optional.ofNullable(produtosPedidosService.findByPedidoAndProduto(pedidoEntity, produtoEntity));
		if (produtosPedidos.isPresent()) {
			if (pedido.getQuantidade() <= produtoEntity.getQuantEstoque()) {
				produtosPedidosService.update(produtosPedidos.get(), pedido.getQuantidade());				
			} else throw new EstoqueInsuficienteException("Estoque insuficiente!");
		} else if (pedido.getQuantidade() <= produtoEntity.getQuantEstoque()) {
			produtosPedidosService.create(pedidoEntity, pedido);
		}
		return pedido;
	}
	
	public void fechar(long numeroPedido) throws PedidoNotFoundException, EstoqueInsuficienteException, ValorNegativoException, ProdutoNotFoundException {
		PedidoEntity pedido = this.getByNumero(numeroPedido);
		List<ProdutosPedidosEntity> listaProdutosPedidos = produtosPedidosService.findByPedido(pedido);
		for (ProdutosPedidosEntity produtosPedidosEntity : listaProdutosPedidos) {
			produtoService.vender(produtosPedidosEntity.getProduto(), produtosPedidosEntity.getQuantidade());
		}
		pedido.setStatus(StatusEnum.FECHADO);
		repository.save(pedido);
	}
	
	public String pagar(Long numeroDoPedido) throws PedidoNotFoundException, NotclosedPedidoException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		if (pedido.getStatus() == StatusEnum.FECHADO) {
			pedido.setDataDoPedido(LocalDate.now());
			pedido.setStatus(StatusEnum.PAGO);
			pedido.setDataEntrega(LocalDate.now().plusDays(3));
			repository.save(pedido);
			return "Pagamento Recebido!";
		}
		throw new NotclosedPedidoException("Favor fechar o pedido antes de efetuar pagamento!"); 
	}
	
	public String transportar(Long numeroDoPedido) throws PedidoNotFoundException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		pedido.setStatus(StatusEnum.TRANSPORTE);
		repository.save(pedido);
		return "Pedido em transporte!";
	}
	
	public String entregar(Long numeroDoPedido) throws PedidoNotFoundException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		pedido.setStatus(StatusEnum.ENTREGUE);
		repository.save(pedido);
		return "Pedido entregue!";
	}
	
	public String delete(Long numeroDoPedido) throws PedidoNotFoundException, PedidoFinalizadoException {
		PedidoEntity pedido = this.getByNumero(numeroDoPedido);
		if(pedido.getStatus() == StatusEnum.ENTREGUE) throw new PedidoFinalizadoException("Pedidos finalizados nao podem ser deletados");
		if (pedido.getStatus() != StatusEnum.RECEBIDO) {
			List<ProdutosPedidosEntity> listaPedProd = produtosPedidosService.findByPedido(pedido);
			for (ProdutosPedidosEntity produtosPedidosEntity : listaPedProd) {
				produtoService.retornaEstoque(produtosPedidosEntity.getProduto(), produtosPedidosEntity.getQuantidade());
			}
		}
		repository.delete(pedido);
		return "Pedido deletado com sucesso!";
	}
	
}
