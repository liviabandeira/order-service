package com.api.produto.application.service;

import com.api.produto.application.responses.ProdutoResponse;
import com.api.produto.domain.models.Produto;
import com.api.produto.infrastructure.exceptions.NotFoundException;
import com.api.produto.infrastructure.repositorys.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public ProdutoResponse findProdutos (String skus) {

        List<String> skuList = Arrays.asList(skus.split(","));

        List<Produto> produtos = new ArrayList<>();
        ProdutoResponse produtoResponse = new ProdutoResponse(produtos);

        for (String sku:skuList){
            try{
                produtos.add(produtoRepository.findBySku(sku));
            }catch (Exception e){
                new NotFoundException("SKU não encontrado");
            }

        }

        produtoResponse.setProdutos(produtos);

        return produtoResponse;
    }

    public Produto updateProduto (Long produtoId, Produto produtoRequest) {

        return produtoRepository.findById(produtoId)
                .map(produto -> {
                    produto.setAltura(produtoRequest.getAltura());
                    produto.setDescricao(produtoRequest.getDescricao());
                    produto.setFabricante(produtoRequest.getFabricante());
                    produto.setLargura(produtoRequest.getLargura());
                    produto.setPeso(produtoRequest.getPeso());
                    produto.setPreco(produtoRequest.getPreco());
                    produto.setSku(produtoRequest.getSku());
                    produto.setProfundidade(produtoRequest.getProfundidade());
                    return produtoRepository.save(produto);
                }).orElseThrow(() -> new NotFoundException("ID " + produtoId +
                        " do produto não foi encontrado"));
    }

    public ResponseEntity<?> deleteProduto(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .map(produto -> {
                    produtoRepository.delete(produto);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new NotFoundException("ID " + produtoId +
                        " do produto não foi encontrado"));
    }
}
