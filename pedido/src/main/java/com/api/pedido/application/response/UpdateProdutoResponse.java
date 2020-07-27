package com.api.pedido.application.response;

import com.api.pedido.domain.models.Pedido;
import com.api.pedido.domain.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UpdateProdutoResponse {

    private Pedido pedido;
    private List<Produto> produtos;

}
