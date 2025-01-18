package com.teste.itau.controller;

import com.teste.itau.dto.ProdutoRequest;
import com.teste.itau.entity.Produtos;
import com.teste.itau.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController target;

    @Mock
    private ProdutoService produtoService;

    @Test
    void CrieProdutoSucesso() {
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setName("Teste Produto");
        produtoRequest.setCategory("Eletrocomputador");
        produtoRequest.setDescription("Eletrocomputador novidade");
        produtoRequest.setPrice(99.99);

        Produtos produto = Produtos.builder()
                .id(1L)
                .name(produtoRequest.getName())
                .category(produtoRequest.getCategory())
                .description(produtoRequest.getDescription())
                .price(produtoRequest.getPrice())
                .build();

        when(produtoService.createProduto(any(ProdutoRequest.class))).thenReturn(produto);

        ResponseEntity<Produtos> response = target.createProduto(produtoRequest);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(produto.getName(), response.getBody().getName());
    }

    @Test
    void GetAllProdutosSucesso() {
        Produtos produto1 = Produtos.builder().id(1L).name("Produto1").build();
        Produtos produto2 = Produtos.builder().id(2L).name("Produto2").build();

        when(produtoService.getAllProdutos()).thenReturn(Arrays.asList(produto1, produto2));

        ResponseEntity<List<Produtos>> response = target.getAllProdutos();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    void GetProdutoIdSucesso() {
        Produtos produto = Produtos.builder().id(1L).name("Produto1").build();

        when(produtoService.getProdutoById(anyLong())).thenReturn(produto);

        ResponseEntity<Produtos> response = target.getProdutosById(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(produto.getName(), response.getBody().getName());
    }

    @Test
    void UpdateProdutoSucesso() {
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setName("Updated Produto");
        produtoRequest.setCategory("Updated Category");
        produtoRequest.setDescription("Updated Description");
        produtoRequest.setPrice(199.99);

        Produtos updatedProduto = Produtos.builder()
                .id(1L)
                .name(produtoRequest.getName())
                .category(produtoRequest.getCategory())
                .description(produtoRequest.getDescription())
                .price(produtoRequest.getPrice())
                .build();

        when(produtoService.updateProduto(anyLong(), any(ProdutoRequest.class))).thenReturn(updatedProduto);

        ResponseEntity<Produtos> response = target.updateProduto(1L, produtoRequest);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(updatedProduto.getName(), response.getBody().getName());
    }

    @Test
    void DeleteProdutoSucesso() {
        doNothing().when(produtoService).deleteProduto(anyLong());

        ResponseEntity<Void> response = target.deleteProduto(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(produtoService, times(1)).deleteProduto(anyLong());
    }
}
