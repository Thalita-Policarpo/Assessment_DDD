package com.infnet.PetFriends_Pedidos.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange almoxarifadoExchange() {
        return new DirectExchange("almoxarifadoExchange");
    }

    @Bean
    public DirectExchange transporteExchange() {
        return new DirectExchange("transporteExchange");
    }

    @Bean
    public Queue atualizarEstoqueQueue() {
        return new Queue("atualizarEstoqueQueue", true);
    }

    @Bean
    public Queue novaEntregaQueue() {
        return new Queue("novaEntregaQueue", true);
    }

    @Bean
    public Binding estoqueBinding(Queue atualizarEstoqueQueue, DirectExchange almoxarifadoExchange) {
        return BindingBuilder.bind(atualizarEstoqueQueue).to(almoxarifadoExchange).with("atualizar.estoque");
    }

    @Bean
    public Binding entregaBinding(Queue novaEntregaQueue, DirectExchange transporteExchange) {
        return BindingBuilder.bind(novaEntregaQueue).to(transporteExchange).with("nova.entrega");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}