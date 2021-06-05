package org.serratec.ecommerce.repositories;

import java.util.List;

import org.serratec.ecommerce.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long>{

	
	List<EnderecoEntity> findAllByAtivoTrue();
	
	EnderecoEntity findByNomeAndCliente(String nome, String cliente);
}
