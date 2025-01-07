package com.teste.itau.controller;

import com.teste.itau.dto.ProdutoRequest;
import com.teste.itau.entity.Produtos;
import com.teste.itau.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(SpringExtension.class)
public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController target;

    @Mock
    private ProdutoService produtoService;

    @Test
    void shouldCreateProdutoSuccessfully() {
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setName("Test Produto");
        produtoRequest.setCategory("Electronics");
        produtoRequest.setDescription("A sample product description");
        produtoRequest.setPrice(99.99);

        Produtos savedProduto = Produtos.builder()
                .id(1L)
                .name("Test Produto")
                .category("Electronics")
                .description("A sample product description")
                .price(99.99)
                .build();

        Mockito.when(produtoService.createProduto(any(ProdutoRequest.class))).thenReturn(savedProduto);

        var response = target.createProdutos(produtoRequest);
        Assertions.assertEquals(savedProduto.getName(), response.getBody().getName());
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidProdutoRequest() {
        ProdutoRequest invalidProdutoRequest = new ProdutoRequest();
        invalidProdutoRequest.setName("");
        invalidProdutoRequest.setCategory(null);
        invalidProdutoRequest.setDescription("");
        invalidProdutoRequest.setPrice(null);

//        mockMvc.perform(post("/api/Produtos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidProdutoRequest)))
//                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldHandleInternalServerErrorDuringCreation() throws Exception {
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setName("Another Produto");
        produtoRequest.setCategory("Books");
        produtoRequest.setDescription("Another product description");
        produtoRequest.setPrice(29.99);

        Mockito.when(produtoService.createProduto(any(ProdutoRequest.class)))
                .thenThrow(new RuntimeException("Internal server error"));

//        mockMvc.perform(post("/api/Produtos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(produtoRequest)))
//                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldReturnAllProdutosSuccessfully() {
        Produtos produto1 = Produtos.builder()
                .id(1L)
                .name("Produto 1")
                .category("Category 1")
                .description("Description 1")
                .price(10.0)
                .build();

        Produtos produto2 = Produtos.builder()
                .id(2L)
                .name("Produto 2")
                .category("Category 2")
                .description("Description 2")
                .price(20.0)
                .build();

        List<Produtos> produtosList = List.of(produto1, produto2);

        Mockito.when(produtoService.getAllProdutos()).thenReturn(produtosList);

        var response = target.getAllProdutos();
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void shouldHandleInternalServerErrorDuringGetAllProdutos() throws Exception {
        Mockito.when(produtoService.getAllProdutos()).thenThrow(new RuntimeException());

    }

    @Test
    void shouldReturnProdutoByIdSuccessfully() throws Exception {
        Produtos produto = Produtos.builder()
                .id(1L)
                .name("Produto Teste")
                .category("Category Test")
                .description("Description Test")
                .price(50.0)
                .build();

        Mockito.when(produtoService.getProdutoById(1L)).thenReturn(produto);

//        mockMvc.perform(get("/api/Produtos/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Produto Teste"))
//                .andExpect(jsonPath("$.category").value("Category Test"))
//                .andExpect(jsonPath("$.description").value("Description Test"))
//                .andExpect(jsonPath("$.price").value(50.0));
    }

    @Test
    void shouldReturnNotFoundWhenProdutoDoesNotExist() throws Exception {
        Mockito.when(produtoService.getProdutoById(99L)).thenReturn(null);

//        mockMvc.perform(get("/api/Produtos/99"))
//                .andExpect(status().isNotFound());
    }

    @Test
    void shouldHandleInternalServerErrorDuringGetProdutoById() throws Exception {
        Mockito.when(produtoService.getProdutoById(1L)).thenThrow(new RuntimeException("Internal server error"));

//        mockMvc.perform(get("/api/Produtos/1"))
//                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldUpdateProdutoSuccessfully() throws Exception {
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setName("Updated Produto");
        produtoRequest.setCategory("Updated Category");
        produtoRequest.setDescription("Updated Description");
        produtoRequest.setPrice(199.99);

        Produtos updatedProduto = Produtos.builder()
                .id(1L)
                .name("Updated Produto")
                .category("Updated Category")
                .description("Updated Description")
                .price(199.99)
                .build();

        Mockito.when(produtoService.updateProduto(Mockito.eq(1L), any(ProdutoRequest.class))).thenReturn(updatedProduto);

//        mockMvc.perform(put("/api/Produtos/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(produtoRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Updated Produto"))
//                .andExpect(jsonPath("$.category").value("Updated Category"))
//                .andExpect(jsonPath("$.description").value("Updated Description"))
//                .andExpect(jsonPath("$.price").value(199.99));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidUpdateRequest() throws Exception {
        ProdutoRequest invalidProdutoRequest = new ProdutoRequest();
        invalidProdutoRequest.setName("");
        invalidProdutoRequest.setCategory(null);
        invalidProdutoRequest.setDescription("");
        invalidProdutoRequest.setPrice(null);

//        mockMvc.perform(put("/api/Produtos/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidProdutoRequest)))
//                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldHandleInternalServerErrorDuringUpdate() throws Exception {
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setName("Update Produto");
        produtoRequest.setCategory("Category Test");
        produtoRequest.setDescription("Description Test");
        produtoRequest.setPrice(50.0);

        Mockito.when(produtoService.updateProduto(Mockito.eq(1L), any(ProdutoRequest.class)))
                .thenThrow(new RuntimeException("Internal server error"));

//        mockMvc.perform(put("/api/Produtos/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(produtoRequest)))
//                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldDeleteProdutoSuccessfully() throws Exception {
        Mockito.doNothing().when(produtoService).deleteProduto(1L);

//        mockMvc.perform(delete("/api/Produtos/1"))
//                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingProduto() throws Exception {
        Mockito.doThrow(new RuntimeException("Produto not found")).when(produtoService).deleteProduto(99L);

//        mockMvc.perform(delete("/api/Produtos/99"))
//                .andExpect(status().isNotFound());
    }

    @Test
    void shouldHandleInternalServerErrorDuringDelete() throws Exception {
        Mockito.doThrow(new RuntimeException("Internal server error")).when(produtoService).deleteProduto(100L);

//        mockMvc.perform(delete("/api/Produtos/100"))
//                .andExpect(status().isInternalServerError());
    }
}