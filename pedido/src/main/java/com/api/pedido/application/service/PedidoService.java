package com.api.pedido.application.service;

import com.api.pedido.application.request.PedidoProdutoRequest;
import com.api.pedido.application.request.UpdatePedidoRequest;
import com.api.pedido.application.response.CreatePedidoResponse;
import com.api.pedido.application.response.PedidoResponse;
import com.api.pedido.domain.models.Pedido;
import com.api.pedido.domain.models.PedidoProduto;
import com.api.pedido.domain.models.Produto;
import com.api.pedido.infrastructure.exceptions.NotFoundException;
import com.api.pedido.infrastructure.repository.PedidoProdutoRepository;
import com.api.pedido.infrastructure.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoProdutoRepository pedidoProdutoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoProdutoService pedidoProdutoService;

    @Value("${produto.base.uri}")
    private String uriBaseProduto;


    public Pedido createPedido(PedidoProdutoRequest pedidoProdutoRequest){

        PedidoService pedidoService = new PedidoService();
        List<String> skus = pedidoProdutoService.getListSku(pedidoProdutoRequest.getProdutos());

        String url = uriBaseProduto;

        // preparando os parametros da url
        for (int i = 0; i < skus.size(); i++) {
            url += skus.get(i);

            if (i == skus.size() - 1) break;

            url += ",";
        }

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<CreatePedidoResponse> result = restTemplate.getForEntity(url, CreatePedidoResponse.class);

        Pedido pedido = new Pedido(null,pedidoProdutoRequest.getPedido().getNomeCliente(),
                pedidoProdutoRequest.getPedido().getTelefone(),0,
                pedidoProdutoRequest.getPedido().getValorDesconto(), 0);

        Pedido pedidoRepo = pedidoRepository.save(pedido);

        Long idPedido = pedidoRepo.getIdPedido();
        List<Produto> produtos = result.getBody().getProdutos();
        for (Produto produto : produtos) {
            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setSku(produto.getSku());
            pedidoProduto.setIdPedido(idPedido);
            for (Produto p2 : pedidoProdutoRequest.getProdutos()){
                if (p2.getSku().equals(produto.getSku())){
                    pedidoProduto.setQntd(p2.getQuantidade());
                    produto.setQuantidade(p2.getQuantidade());
                }
            }
            pedidoProduto.setPreco(produto.getPreco());

            pedidoProdutoRepository.save(pedidoProduto);
        }

        // Recalculando os valores do pedido
        double valorProdutoRecalculado = pedidoService.recalculaValorProduto(produtos);
        pedidoRepo.setValorProduto(valorProdutoRecalculado);
        pedidoRepo.setValorTotal(pedidoService.recalculaValorTotal(valorProdutoRecalculado, pedidoRepo.getValorDesconto()));

        //Atualiza pedido
        return update(pedidoRepo.getIdPedido(), pedidoRepo);
    }

    public Pedido update(Long pedidoId, Pedido pedido) {
        return pedidoRepository.findById(pedidoId)
                .map(produto -> {
                    produto.setNomeCliente(pedido.getNomeCliente());
                    produto.setTelefone(pedido.getTelefone());
                    produto.setValorDesconto(pedido.getValorDesconto());
                    produto.setValorProduto(pedido.getValorProduto());
                    produto.setValorTotal(pedido.getValorTotal());
                    return pedidoRepository.save(produto);
                }).orElseThrow(() -> new NotFoundException("ID " + pedidoId +
                        " do pedido não foi encontrado"));
    }

    public ResponseEntity<?> deletaPedido (Long pedidoId){

        //deleta primeiro do PedidoProduto
        List<PedidoProduto> pedidoProdutos = pedidoProdutoRepository.findByIdPedido(pedidoId);
        for (PedidoProduto pedidoProduto : pedidoProdutos) {
            pedidoProdutoRepository.delete(pedidoProduto);
        }

        return pedidoRepository.findById(pedidoId)
                .map(produto -> {
                    pedidoRepository.delete(produto);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new NotFoundException("ID " + pedidoId +
                        " do pedido não foi encontrado"));
    }

    public double recalculaValorProduto(List<Produto> produtos){

        double valorProduto = 0;

        for (Produto produto: produtos) {
            valorProduto += (produto.getQuantidade() * produto.getPreco());
        }
        return valorProduto;

    }

    public double recalculaValorTotal(double valorTotalProdutos, double valorDesconto){

        return valorTotalProdutos - (valorTotalProdutos * valorDesconto/100);
    }

}
