package com.teste.itau.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.itau.dto.RequisicaoProduto;
import com.teste.itau.exception.GlobalExceptionHandler;
import com.teste.itau.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerValidationTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    void deveRetornarErroQuandoNomeEstiverVazio() throws Exception {
        RequisicaoProduto requisicaoProduto = new RequisicaoProduto();
        requisicaoProduto.setNome("");
        requisicaoProduto.setCategoria("Categoria");
        requisicaoProduto.setDescricao("Descrição válida");
        requisicaoProduto.setPreco(10.0);

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requisicaoProduto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("O nome do produto é obrigatório."));
    }

    @Test
    void deveRetornarErroQuandoPrecoForNegativo() throws Exception {
        RequisicaoProduto requisicaoProduto = new RequisicaoProduto();
        requisicaoProduto.setNome("Produto válido");
        requisicaoProduto.setCategoria("Categoria");
        requisicaoProduto.setDescricao("Descrição válida");
        requisicaoProduto.setPreco(-5.0);

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requisicaoProduto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.preco").value("O preço do produto deve ser maior ou igual a zero."));
    }

    @Test
    void deveRetornarErroGenericoParaExcecao() throws Exception {
        when(produtoService.createProduto(any())).thenThrow(new RuntimeException("Erro inesperado"));

        RequisicaoProduto requisicaoProduto = new RequisicaoProduto();
        requisicaoProduto.setNome("Produto válido");
        requisicaoProduto.setCategoria("Categoria");
        requisicaoProduto.setDescricao("Descrição válida");
        requisicaoProduto.setPreco(10.0);

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requisicaoProduto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Erro inesperado"));
    }
}
