package com.teste.itau.service.impl;

import com.teste.itau.config.rabbitMq.RabbitMQConfig;
import com.teste.itau.dto.ProdutoRequest;
import com.teste.itau.entity.Produtos;
import com.teste.itau.repository.ProdutoRepository;
import com.teste.itau.service.ProdutoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository ProdutosRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public List<Produtos> getAllProdutos() {
        return ProdutosRepository.findAll();
    }


    @Override
    public Produtos getProdutoById(Long id) {
        return ProdutosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }

    @Override
    public Produtos createProduto(ProdutoRequest produtoRequest) {
        Produtos produto = Produtos.builder()
                .name(produtoRequest.getName())
                .price(produtoRequest.getPrice())
                .category(produtoRequest.getCategory())
                .description(produtoRequest.getDescription())
                .build();

        Produtos savedProduto = produtoRepository.save(produto);

        // Envia mensagem para a fila
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, "Produto criado: " + savedProduto);

        return savedProduto;
    }


    @Override
    public Produtos updateProduto(Long id, ProdutoRequest produtoRequest) {
        Produtos existingProdutos = getProdutoById(id);
        Logger.getGlobal().info(existingProdutos.toString());
        existingProdutos = Produtos.builder()
                .id(id)
                .name(produtoRequest.getName())
                .price(produtoRequest.getPrice())
                .category(produtoRequest.getCategory())
                .description(produtoRequest.getDescription())
                .build();

        return ProdutosRepository.save(existingProdutos);
    }

    @Override
    public void deleteProduto(Long id) {
        ProdutosRepository.deleteById(id);
    }
}
