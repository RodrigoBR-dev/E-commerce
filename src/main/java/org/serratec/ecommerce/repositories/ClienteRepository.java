package org.serratec.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{

	Optional<ClienteEntity> findByAtivoTrueAndUserNameOrEmail(String userName, String email);
	
	List<ClienteEntity> findAllByAtivoTrue();

}
