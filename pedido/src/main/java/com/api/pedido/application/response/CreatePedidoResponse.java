package com.api.pedido.application.response;

import com.api.pedido.domain.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatePedidoResponse {

    private List<Produto> produtos;

}
