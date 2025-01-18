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
    public ProdutoService produtoService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<Produtos>> getAllProdutos() {
        return new ResponseEntity<>(produtoService.getAllProdutos(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Produtos> getProdutosById(@PathVariable Long id) {
        return new ResponseEntity<>(produtoService.getProdutoById(id), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<Produtos> createProduto(@RequestBody ProdutoRequest produtoRequest) {
        Produtos produto = produtoService.createProduto(produtoRequest);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }


    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Produtos> updateProduto(@PathVariable Long id, @RequestBody ProdutoRequest produtoRequest) {
        Produtos updatedProduto = produtoService.updateProduto(id, produtoRequest);
        return new ResponseEntity<>(updatedProduto, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProduto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

