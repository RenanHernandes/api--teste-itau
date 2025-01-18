package com.teste.itau.service.impl;

import com.teste.itau.dto.RequisicaoProduto;
import com.teste.itau.entity.Produtos;
import com.teste.itau.repository.ProdutoRepository;
import com.teste.itau.service.messaging.PublicadorDeMensagemProduto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceImplTest {

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PublicadorDeMensagemProduto publicadorDeMensagem;

    @Test
    void deveRetornarTodosOsProdutos() {
        Produtos produto1 = Produtos.builder().id(1L).nome("Produto 1").build();
        Produtos produto2 = Produtos.builder().id(2L).nome("Produto 2").build();

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));

        List<Produtos> produtos = produtoService.getAllProdutos();

        assertNotNull(produtos);
        assertEquals(2, produtos.size());
    }

    @Test
    void deveRetornarProdutoPorId() {
        Produtos produto = Produtos.builder().id(1L).nome("Produto 1").build();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));

        Produtos resultado = produtoService.getProdutoById(1L);

        assertNotNull(resultado);
        assertEquals("Produto 1", resultado.getNome());
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

        when(produtoRepository.save(any(Produtos.class))).thenReturn(produtoSalvo);

        Produtos resultado = produtoService.createProduto(requisicaoProduto);

        assertNotNull(resultado);
        assertEquals("Novo Produto", resultado.getNome());
        verify(publicadorDeMensagem, times(1)).publicarMensagemProdutoCriado(produtoSalvo);
    }

    @Test
    void deveAtualizarProduto() {
        Produtos produtoExistente = Produtos.builder().id(1L).nome("Produto Antigo").build();
        RequisicaoProduto requisicaoProduto = new RequisicaoProduto();
        requisicaoProduto.setNome("Produto Atualizado");
        requisicaoProduto.setCategoria("Nova Categoria");
        requisicaoProduto.setDescricao("Nova Descrição");
        requisicaoProduto.setPreco(20.0);

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produtoExistente));
        when(produtoRepository.save(any(Produtos.class))).thenReturn(produtoExistente);

        Produtos resultado = produtoService.updateProduto(1L, requisicaoProduto);

        assertNotNull(resultado);
        assertEquals("Produto Atualizado", resultado.getNome());
    }

    @Test
    void deveDeletarProduto() {
        doNothing().when(produtoRepository).deleteById(anyLong());

        produtoService.deleteProduto(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }
}
