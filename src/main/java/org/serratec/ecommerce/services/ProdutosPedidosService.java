package org.serratec.ecommerce.services;

import java.util.List;

import org.serratec.ecommerce.dto.PedidoDTO;
import org.serratec.ecommerce.entities.PedidoEntity;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.entities.ProdutosPedidosEntity;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.repositories.ProdutosPedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutosPedidosService {
	
	@Autowired
	ProdutosPedidosRepository repository;
	
	@Autowired
	ProdutoService produtoService;
	
	public List<ProdutosPedidosEntity> findByPedido(PedidoEntity pedido) {
		return repository.findByPedido(pedido);
	}
	public ProdutosPedidosEntity findByPedidoAndProduto(PedidoEntity pedido, ProdutoEntity produto) {
		return repository.findByPedidoAndProduto(pedido, produto);
	}
	
	public void create(PedidoEntity pedido, PedidoDTO dto) throws ProdutoNotFoundException {
		ProdutosPedidosEntity produtosPedidos = new ProdutosPedidosEntity();
		produtosPedidos.setPedido(pedido);
		produtosPedidos.setQuantidade(dto.getQuantidade());
		ProdutoEntity produto = produtoService.findByNome(dto.getProduto());
		produtosPedidos.setProduto(produto);
		produtosPedidos.setPreco(produto.getPreco());
		repository.save(produtosPedidos);
	}
}
