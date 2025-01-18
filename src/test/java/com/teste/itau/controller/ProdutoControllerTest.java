package com.teste.itau.controller;

import com.teste.itau.dto.RequisicaoProduto;
import com.teste.itau.entity.Produtos;
import com.teste.itau.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    @Test
    void deveRetornarTodosOsProdutos() {
        Produtos produto1 = Produtos.builder().id(1L).nome("Produto 1").build();
        Produtos produto2 = Produtos.builder().id(2L).nome("Produto 2").build();

        when(produtoService.getAllProdutos()).thenReturn(Arrays.asList(produto1, produto2));

        ResponseEntity<List<Produtos>> resposta = produtoController.obterTodosOsProdutos();

        assertNotNull(resposta.getBody());
        assertEquals(2, resposta.getBody().size());
    }

    @Test
    void deveRetornarProdutoPorId() {
        Produtos produto = Produtos.builder().id(1L).nome("Produto 1").build();

        when(produtoService.getProdutoById(anyLong())).thenReturn(produto);

        ResponseEntity<Produtos> resposta = produtoController.obterProdutoPorId(1L);

        assertNotNull(resposta.getBody());
        assertEquals("Produto 1", resposta.getBody().getNome());
    }

    @Test
    void deveCriarProduto() {
        RequisicaoProduto requisicaoProduto = new RequisicaoProduto();
        requisicaoProduto.setNome("Novo Produto");
        requisicaoProduto.setCategoria("Categoria");
        requisicaoProduto.setDescricao("Descrição do Produto");
        requisicaoProduto.setPreco(10.0);

        Produtos produtoSalvo = Produtos.builder()
                .id(1L)
                .nome(requisicaoProduto.getNome())
                .categoria(requisicaoProduto.getCategoria())
                .descricao(requisicaoProduto.getDescricao())
                .preco(requisicaoProduto.getPreco())
                .build();

        when(produtoService.createProduto(any(RequisicaoProduto.class))).thenReturn(produtoSalvo);

        ResponseEntity<Produtos> resposta = produtoController.criarProduto(requisicaoProduto);

        assertNotNull(resposta.getBody());
        assertEquals("Novo Produto", resposta.getBody().getNome());
    }

    @Test
    void deveAtualizarProduto() {
        RequisicaoProduto requisicaoProduto = new RequisicaoProduto();
        requisicaoProduto.setNome("Produto Atualizado");
        requisicaoProduto.setCategoria("Nova Categoria");
        requisicaoProduto.setDescricao("Nova Descrição");
        requisicaoProduto.setPreco(20.0);

        Produtos produtoAtualizado = Produtos.builder()
                .id(1L)
                .nome(requisicaoProduto.getNome())
                .categoria(requisicaoProduto.getCategoria())
                .descricao(requisicaoProduto.getDescricao())
                .preco(requisicaoProduto.getPreco())
                .build();

        when(produtoService.updateProduto(anyLong(), any(RequisicaoProduto.class))).thenReturn(produtoAtualizado);

        ResponseEntity<Produtos> resposta = produtoController.atualizarProduto(1L, requisicaoProduto);

        assertNotNull(resposta.getBody());
        assertEquals("Produto Atualizado", resposta.getBody().getNome());
    }

    @Test
    void deveDeletarProduto() {
        doNothing().when(produtoService).deleteProduto(anyLong());

        ResponseEntity<Void> resposta = produtoController.deletarProduto(1L);

        assertEquals(204, resposta.getStatusCodeValue());
        verify(produtoService, times(1)).deleteProduto(1L);
    }
}
