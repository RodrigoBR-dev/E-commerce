package org.serratec.ecommerce.repositories;

import org.serratec.ecommerce.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>{

	ProdutoEntity findByNome(String nome);
}
