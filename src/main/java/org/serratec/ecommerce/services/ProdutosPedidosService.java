package org.serratec.ecommerce.services;


import org.serratec.ecommerce.entities.PedidoEntity;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.entities.ProdutosPedidos;
import org.serratec.ecommerce.exceptions.EstoqueInsuficienteException;
import org.serratec.ecommerce.exceptions.ProdutoNotFoundException;
import org.serratec.ecommerce.repositories.ProdutosPedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutosPedidosService {

	@Autowired
	ProdutosPedidosRepository repository;
	@Autowired
	ProdutoService prodService;

	
	public ProdutosPedidos create(ProdutoEntity produto,PedidoEntity pedido,Integer quantidade) throws EstoqueInsuficienteException, ProdutoNotFoundException {
		ProdutosPedidos prodPedido = new ProdutosPedidos();
		prodService.vender(produto.getId(), quantidade);
		prodPedido.setPedido(pedido);
		prodPedido.setProduto(produto);
		prodPedido.setPreco(produto.getPreco() * quantidade);
		prodPedido.setQuantidade(quantidade);
		
		return repository.save(prodPedido);
	}
	
	
}
