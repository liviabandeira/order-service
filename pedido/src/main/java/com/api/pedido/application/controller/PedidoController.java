package com.api.pedido.application.controller;

import com.api.pedido.application.request.UpdatePedidoRequest;
import com.api.pedido.application.service.PedidoProdutoService;
import com.api.pedido.application.service.PedidoService;
import com.api.pedido.application.request.PedidoProdutoRequest;
import com.api.pedido.application.response.PedidoResponse;
import com.api.pedido.domain.models.Pedido;
import com.api.pedido.infrastructure.repository.PedidoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoProdutoService pedidoProdutoService;

    @ApiOperation(value = "Busca de pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o pedido solicitado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @GetMapping(value = "/{idPedido}",produces="application/json")
    public PedidoResponse findPedidoById(@PathVariable Long idPedido){
        return pedidoProdutoService.getProdutoByIdPedido(idPedido);
    }

    @ApiOperation(value = "Retorna uma lista de Pedidos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de pedidos"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @GetMapping(produces="application/json")
    public Page<Pedido> findAll(Pageable pageable){
        return pedidoRepository.findAll(pageable);
    }


    @ApiOperation(value = "Insere um pedido com produtos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o pedido criado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
   @PostMapping(produces = "application/json") @ResponseStatus(value = HttpStatus.CREATED)
    public Pedido createPedido(@RequestBody PedidoProdutoRequest pedidoProdutoRequest) {
        return pedidoService.createPedido(pedidoProdutoRequest);
    }

    @ApiOperation(value = "Atualiza um pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o pedido atualizado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @PutMapping("/{pedidoId}")
    public Pedido updatePedido(@PathVariable Long pedidoId, @RequestBody Pedido pedido) {
        return pedidoService.update(pedidoId, pedido);
    }

    @ApiOperation(value = "Deleta um pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna mensagem"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno"),
    })
    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<?> deletePedido(@PathVariable Long pedidoId) {

        return pedidoService.deletaPedido(pedidoId);
    }

}
