package org.serratec.ecommerce.repositories;

import org.serratec.ecommerce.entities.ProdutosPedidos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosPedidosRepository extends JpaRepository<ProdutosPedidos, Long> {
}
