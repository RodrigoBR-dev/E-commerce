package org.serratec.ecommerce.repositories;

import java.util.Optional;

import org.serratec.ecommerce.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
	
	Optional<CategoriaEntity> findByNome(String nome);
}
