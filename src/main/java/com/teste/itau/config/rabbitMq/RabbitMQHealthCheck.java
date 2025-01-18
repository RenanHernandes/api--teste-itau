package com.teste.itau.config.rabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQHealthCheck {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void checkConnection() {
        try {
            rabbitTemplate.convertAndSend("produtoQueue", "Test Message");
            System.out.println("Conexão com RabbitMQ bem-sucedida!");
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao RabbitMQ: " + e.getMessage());
        }
    }
}
