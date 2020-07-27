package com.api.pedido.application.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProdutoRequest {

    private List<String> sku;
}
