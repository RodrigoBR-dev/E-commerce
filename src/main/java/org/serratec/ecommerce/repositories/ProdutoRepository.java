package org.serratec.ecommerce.repositories;

import java.util.List;

import org.serratec.ecommerce.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>{

	List<ProdutoEntity> findByNome(String nome);
}
