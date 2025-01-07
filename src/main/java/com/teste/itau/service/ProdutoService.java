package com.teste.itau.service;

import java.util.List;
import com.teste.itau.dto.ProdutoRequest;
import com.teste.itau.entity.Produtos;

public interface ProdutoService {
    List<Produtos> getAllProdutos();
    Produtos getProdutoById(Long id);
    Produtos createProduto(ProdutoRequest produtoRequest);
    Produtos updateProduto(Long id, ProdutoRequest ProdutoRequest);
    void deleteProduto(Long id);
}
