package com.teste.itau.service;

import java.util.List;
import com.teste.itau.dto.RequisicaoProduto;
import com.teste.itau.entity.Produtos;

public interface ProdutoService {
    List<Produtos> getAllProdutos();
    Produtos getProdutoById(Long id);
    Produtos createProduto(RequisicaoProduto requisicaoProduto);
    Produtos updateProduto(Long id, RequisicaoProduto RequisicaoProduto);
    void deleteProduto(Long id);
}
