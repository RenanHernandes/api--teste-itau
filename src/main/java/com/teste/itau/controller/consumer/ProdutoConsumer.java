package com.teste.itau.controller.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConsumer {

    @RabbitListener(queues = "produtoQueue")
    public void consumeMessage(String message) {
        System.out.println("Mensagem recebida: " + message);
    }
}
