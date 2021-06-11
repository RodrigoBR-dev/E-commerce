package org.serratec.ecommerce.repositories;

import org.serratec.ecommerce.entities.ImagemEntity;
import org.serratec.ecommerce.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemRepository extends JpaRepository<ImagemEntity,Long>{

	ImagemEntity findByProdutoNome(String nome);

	ImagemEntity findByProduto(ProdutoEntity produto);

	ImagemEntity findByProdutoId(Long id);

	ImagemEntity findById(int i);

}