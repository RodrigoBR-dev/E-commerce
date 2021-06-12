package org.serratec.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{

	Optional<ClienteEntity> findByAtivoTrueAndUserNameOrEmailOrCpf(String userName, String email, String cpf);
	
	List<ClienteEntity> findAllByAtivoTrue();
	
	Optional<ClienteEntity> findByUserNameOrEmailOrCpf(String userName, String email, String cpf);

	Optional<ClienteEntity> findByUserName(String username);

	Optional<ClienteEntity> findByAtivoFalseAndUserNameOrEmailOrCpf(String userName, String email,
			String cpf);

}
