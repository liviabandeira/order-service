package com.api.produto;

import com.api.produto.application.controllers.ProdutoController;
import com.api.produto.domain.models.Produto;
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

@SpringBootTest
@AutoConfigureMockMvc
class ApiProdutosApplicationTests {

	@Autowired
	private ProdutoController produtoController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createProdutoTest() throws Exception {
		final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
				.post("/produtos")
				.content(asJsonString(new Produto(1l, "Teclado", "TEC-0001", 0.5, 66.9, 15, 0.9, "Fabricante Teste", 154.78, 1)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.sku").exists());
	}

	@Test
	void findAllProdutoTest() throws Exception {

		mockMvc.perform( MockMvcRequestBuilders
				.get("/produtos")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void updateProdutoTest() throws Exception {

		mockMvc.perform( MockMvcRequestBuilders
				.post("/produtos")
				.content(asJsonString(new Produto(2l,"Teclado","TEC-0001",0.5,66.9,15,0.9,"Fabricante Teste",154.78,1 )))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		mockMvc.perform( MockMvcRequestBuilders
				.put("/produtos/{produtoId}", 2l)
				.content(asJsonString(new Produto(2l,"Teclado","TEC-0001",0.5,66.9,15,0.9,"Fabricante SA",100.0, 1)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.fabricante").value("Fabricante SA"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.preco").value("100.0"));
	}

	@Test
	void removeProdutoTest() throws Exception {

		mockMvc.perform( MockMvcRequestBuilders
				.delete("/produtos/{produtoId}", 1l)
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
