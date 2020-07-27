package com.api.pedido.application.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PedidoRequest {

    private String nomeCliente;
    private String telefone;
    private double valorDesconto;
}
