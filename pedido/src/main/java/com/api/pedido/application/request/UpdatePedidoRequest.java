package com.api.pedido.application.request;

import com.api.pedido.domain.models.Pedido;
import com.api.pedido.domain.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class UpdatePedidoRequest {

    private Pedido pedido;
    private List<Produto> produtos;
}
