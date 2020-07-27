package com.api.pedido.application.response;

import com.api.pedido.domain.models.Pedido;
import com.api.pedido.domain.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidoResponse {

    private Optional<Pedido> pedido;
    private List<Produto> produtos;
}
