package com.api.pedido;

import com.api.pedido.application.controller.PedidoController;
import com.api.pedido.application.request.PedidoProdutoRequest;
import com.api.pedido.application.request.PedidoRequest;
import com.api.pedido.domain.models.Pedido;
import com.api.pedido.domain.models.Produto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoApplicationTests {

	@Autowired
	private PedidoController pedidoController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createPedidoTest() throws Exception {

		Produto teclado = new Produto("TEC-01",12,2);
		List<Produto> produtos = new ArrayList<>();
		produtos.add(teclado);

		PedidoRequest pedido = new PedidoRequest("Cliente 1", "283783",
				20);

		final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post("/pedidos")
				.content(asJsonString(new PedidoProdutoRequest(pedido,produtos)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.idPedido").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.valorTotal").value(80));
	}

	@Test
	void findAllPedidoTest() throws Exception {

		mockMvc.perform( MockMvcRequestBuilders
				.get("/pedidos")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void updatePedidoTest() throws Exception {

		Produto teclado = new Produto("TEC-01",12,2);
		List<Produto> produtos = new ArrayList<>();
		produtos.add(teclado);

		PedidoRequest pedido = new PedidoRequest("Cliente 1", "283783",
				90);

		final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post("/pedidos")
				.content(asJsonString(new PedidoProdutoRequest(pedido,produtos)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.idPedido").exists());

		mockMvc.perform( MockMvcRequestBuilders
				.put("/pedidos/{pedidoId}", 3l)
				.content(asJsonString(new Pedido(3l,"Cliente2", "293843948", 20,30,10)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nomeCliente").value("Cliente2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.valorTotal").value("10.0"));
	}

	@Test
	void removePedidoTest() throws Exception {

		Produto teclado = new Produto("TEC-01",12,2);
		List<Produto> produtos = new ArrayList<>();
		produtos.add(teclado);

		PedidoRequest pedido = new PedidoRequest("Cliente 1", "283783",
				0);

		final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post("/pedidos")
				.content(asJsonString(new PedidoProdutoRequest(pedido,produtos)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.idPedido").exists());

		mockMvc.perform( MockMvcRequestBuilders
				.delete("/pedidos/{pedidoId}", 2l)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
