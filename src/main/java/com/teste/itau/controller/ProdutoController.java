package com.teste.itau.controller;

import com.teste.itau.dto.RequisicaoProduto;
import com.teste.itau.entity.Produtos;
import com.teste.itau.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "Produtos", description = "Gerenciamento de produtos")
@RestController
@RequestMapping("/api/produtos")
@Validated
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos cadastrados.")
    @GetMapping
    public ResponseEntity<List<Produtos>> obterTodosOsProdutos() {
        List<Produtos> produtos = produtoService.getAllProdutos();
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Obter produto por ID", description = "Busca um produto pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Produtos> obterProdutoPorId(@PathVariable Long id) {
        Produtos produto = produtoService.getProdutoById(id);
        return ResponseEntity.ok(produto);
    }

    @Operation(summary = "Criar um novo produto", description = "Cadastra um novo produto no sistema.")
    @PostMapping
    public ResponseEntity<Produtos> criarProduto(@Valid @RequestBody RequisicaoProduto requisicaoProduto) {
        Produtos novoProduto = produtoService.createProduto(requisicaoProduto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Produtos> atualizarProduto(@PathVariable Long id, @Valid @RequestBody RequisicaoProduto requisicaoProduto) {
        Produtos produtoAtualizado = produtoService.updateProduto(id, requisicaoProduto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(summary = "Deletar produto", description = "Remove um produto pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }
}
