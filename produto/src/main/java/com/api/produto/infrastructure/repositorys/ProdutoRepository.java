package com.api.produto.infrastructure.repositorys;

import com.api.produto.domain.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findBySku(String sku);
}
