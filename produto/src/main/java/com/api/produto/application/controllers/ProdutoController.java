package com.api.produto.application.controllers;

import com.api.produto.application.responses.ProdutoResponse;
import com.api.produto.application.service.ProdutoService;
import com.api.produto.domain.models.Produto;
import com.api.produto.infrastructure.exceptions.NotFoundException;
import com.api.produto.infrastructure.repositorys.ProdutoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Api
@RestController
@RequestMapping("/produtos")

public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @ApiOperation(value = "Retorna uma lista de Produtos")
    @ApiResponses(value = {
            @ApiResponse(code = 200,  message = "Retorna a lista de Produtos"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @GetMapping(produces="application/json")
    public Page<Produto> findAll(Pageable pageable){
        return produtoRepository.findAll(pageable);
    }


    @ApiOperation(value = "Cadastra um produto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o produto cadastrado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @PostMapping(produces="application/json")
    public Produto cadastraProduto(@Valid @RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @GetMapping(value = "/{skus}", produces="application/json")
    public ProdutoResponse findProdutos(@PathVariable String skus) {

        return produtoService.findProdutos(skus);
    }


    @ApiOperation(value = "Atualiza um produto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o produto atualizado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @PutMapping("/{produtoId}")
    public Produto updateProduto(@PathVariable Long produtoId,
                                   @Valid @RequestBody Produto produto) {
        return produtoService.updateProduto(produtoId, produto);
    }


    @ApiOperation(value = "Remove um produto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o produto id do Produto que foi removido"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @DeleteMapping("/{produtoId}")
    public ResponseEntity<?> deleteProduto(@PathVariable Long produtoId) {

        return produtoService.deleteProduto(produtoId);
    }
}
