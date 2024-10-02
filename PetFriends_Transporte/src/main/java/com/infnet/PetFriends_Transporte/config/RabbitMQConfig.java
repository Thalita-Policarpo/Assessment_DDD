package com.infnet.PetFriends_Transporte.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue novaEntregaQueue() {
        return new Queue("novaEntregaQueue", true);
    }
}