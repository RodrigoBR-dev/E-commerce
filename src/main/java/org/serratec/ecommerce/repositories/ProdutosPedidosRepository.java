package org.serratec.ecommerce.repositories;

import java.util.List;

import org.serratec.ecommerce.entities.PedidoEntity;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.serratec.ecommerce.entities.ProdutosPedidosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosPedidosRepository extends JpaRepository<ProdutosPedidosEntity, Long> {

	List<ProdutosPedidosEntity> findByPedido(PedidoEntity pedido);
	ProdutosPedidosEntity findByPedidoAndProduto(PedidoEntity pedido, ProdutoEntity produto);
	List<ProdutosPedidosEntity>findByProduto(ProdutoEntity prodEntity);
}
