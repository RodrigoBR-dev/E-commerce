package org.serratec.ecommerce.repositories;

import org.serratec.ecommerce.entities.ImagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemRepository extends JpaRepository<ImagemEntity,Long>{

	ImagemEntity findByProdutoNome(String nome);

}
