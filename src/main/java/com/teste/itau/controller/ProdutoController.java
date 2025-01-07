package com.teste.itau.controller;


import com.teste.itau.dto.ProdutoRequest;
import com.teste.itau.entity.Produtos;
import com.teste.itau.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/Produtos")
public class ProdutoController {

    @Autowired
    public ProdutoService ProdutoService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<Produtos>> getAllProdutos() {
        return new ResponseEntity<>(ProdutoService.getAllProdutos(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Produtos> getProdutosById(@PathVariable Long id) {
        return new ResponseEntity<>(ProdutoService.getProdutoById(id), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<Produtos> createProdutos(@RequestBody ProdutoRequest produtos) {
        return new ResponseEntity<>(ProdutoService.createProduto(produtos), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Produtos> updateProdutos(@PathVariable Long id, @RequestBody ProdutoRequest produtoRequest) {
        return new ResponseEntity<>(ProdutoService.updateProduto(id, produtoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProdutos(@PathVariable Long id) {
        ProdutoService.deleteProduto(id);
    }
}

