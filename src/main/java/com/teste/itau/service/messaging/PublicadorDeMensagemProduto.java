package com.teste.itau.service.messaging;

import com.teste.itau.entity.Produtos;
import com.teste.itau.config.rabbitMq.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PublicadorDeMensagemProduto {

    private final RabbitTemplate rabbitTemplate;

    public PublicadorDeMensagemProduto(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarMensagemProdutoCriado(Produtos produto) {
        String mensagem = "Produto criado: " + produto;
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, mensagem);
    }
}
