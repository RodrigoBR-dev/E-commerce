package org.serratec.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.ClienteEntity;
import org.serratec.ecommerce.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long>{

	
	List<EnderecoEntity> findAllByAtivoTrueAndCliente(ClienteEntity cliente);
	
	Optional<EnderecoEntity> findByNomeAndCliente(String nome, ClienteEntity cliente);
}
