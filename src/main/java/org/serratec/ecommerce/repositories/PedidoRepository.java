package org.serratec.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.ClienteEntity;
import org.serratec.ecommerce.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long>{
	
	Optional<PedidoEntity> findByNumeroDoPedido(Long numeroDoPedido);

	List<PedidoEntity> findByCliente(ClienteEntity cliente);
}
