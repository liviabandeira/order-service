package com.api.pedido.application.request;

import com.api.pedido.domain.models.Pedido;
import com.api.pedido.domain.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidoProdutoRequest {

    private PedidoRequest pedido;
    private List<Produto> produtos;
}
