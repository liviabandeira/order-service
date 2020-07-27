package com.api.produto.application.responses;

import com.api.produto.domain.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProdutoResponse {

    private List<Produto> produtos;
}
