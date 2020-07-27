package com.api.pedido.infrastructure.repository;

import com.api.pedido.domain.models.PedidoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoProdutoRepository extends JpaRepository<PedidoProduto, Long> {

     List<PedidoProduto> findByIdPedido(Long idPedido);
     List<PedidoProduto> findBySku(String sku);
}
