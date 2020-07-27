package com.api.pedido.application.service;

import com.api.pedido.application.request.ProdutoRequest;
import com.api.pedido.application.response.CreatePedidoResponse;
import com.api.pedido.application.response.PedidoResponse;
import com.api.pedido.domain.models.Pedido;
import com.api.pedido.domain.models.PedidoProduto;
import com.api.pedido.domain.models.Produto;
import com.api.pedido.infrastructure.repository.PedidoProdutoRepository;
import com.api.pedido.infrastructure.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoProdutoService {

    @Autowired
    PedidoProdutoRepository pedidoProdutoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;


    @Value("${produto.base.uri}")
    private String uriBase;

    public List<PedidoProduto> findPedidoProduto(Long idPedido) {
        return pedidoProdutoRepository.findByIdPedido(idPedido);
    }

    public List<String> getSkuPedidoProduto(Long idPedido) {

        List<PedidoProduto> pedidoProdutos = findPedidoProduto(idPedido);

        List<String> skuList = new ArrayList<>();

        for(PedidoProduto pedidoProduto : pedidoProdutos) {
            skuList.add(pedidoProduto.getSku());
        }

        return skuList;
    }

    public List<String> getListSku(List<Produto> produtos) {
        List<String> skuList = new ArrayList<>();
        for(Produto produto : produtos) {
            skuList.add(produto.getSku());
        }
        return skuList;

    }

    public PedidoResponse getProdutoByIdPedido(Long idPedido) {

        List<String> skus = getSkuPedidoProduto(idPedido);

        ProdutoRequest produtoRequest = new ProdutoRequest(skus);

        String url = uriBase;

        // preparando os parametros da url
        for (int i = 0; i < skus.size(); i++) {
            url += skus.get(i);

            if (i == skus.size() - 1) break;

            url += ",";
        }

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<CreatePedidoResponse> result = restTemplate.getForEntity(url, CreatePedidoResponse.class);


        Optional<Pedido> pedido = pedidoRepository.findById(idPedido);

        PedidoResponse pedidoResponse = new PedidoResponse(pedido, result.getBody().getProdutos());
        return pedidoResponse;

    }
}
