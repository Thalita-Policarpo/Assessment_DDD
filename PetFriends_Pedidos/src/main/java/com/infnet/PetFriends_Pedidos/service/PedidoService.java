package com.infnet.PetFriends_Pedidos.service;

import com.infnet.PetFriends_Pedidos.domain.Pedido;
import com.infnet.PetFriends_Pedidos.dto.AtualizarEstoqueMessage;
import com.infnet.PetFriends_Pedidos.dto.NovaEntregaMessage;
import com.infnet.PetFriends_Pedidos.dto.ProdutoEstoqueResponse;
import com.infnet.PetFriends_Pedidos.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    public PedidoService(PedidoRepository pedidoRepository, RabbitTemplate rabbitTemplate) {
        this.pedidoRepository = pedidoRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = new RestTemplate();
    }

    public Pedido criarPedido(Pedido pedido) {
        String url = "http://localhost:8081/produtos/" + pedido.getProdutoId();
        try {
            ProdutoEstoqueResponse produto = restTemplate.getForObject(url, ProdutoEstoqueResponse.class);
            if (produto != null && produto.getQuantidadeEstoque() >= pedido.getQuantidade()) {
                Pedido novoPedido = pedidoRepository.save(pedido);
                log.info("Pedido criado com sucesso: {}", novoPedido);

                // Enviar mensagem para atualizar estoque
                AtualizarEstoqueMessage estoqueMessage = new AtualizarEstoqueMessage();
                estoqueMessage.setProdutoId(novoPedido.getProdutoId());
                estoqueMessage.setQuantidadeSolicitada(novoPedido.getQuantidade());

                rabbitTemplate.convertAndSend("almoxarifadoExchange", "atualizar.estoque", estoqueMessage);
                log.info("Mensagem enviada para Almoxarifado: {}", estoqueMessage);

                // Enviar mensagem para criar uma nova entrega
                NovaEntregaMessage entregaMessage = new NovaEntregaMessage();
                entregaMessage.setEnderecoId(novoPedido.getEnderecoId());

                rabbitTemplate.convertAndSend("transporteExchange", "nova.entrega", entregaMessage);
                log.info("Mensagem enviada para Transporte: {}", entregaMessage);

                return novoPedido;
            } else {
                throw new RuntimeException("Quantidade em estoque insuficiente. Estoque atual: " + (produto != null ? produto.getQuantidadeEstoque() : 0));
            }
        } catch (Exception e) {
            log.error("Erro ao criar pedido", e);
            throw new RuntimeException("Erro ao criar pedido: " + e.getMessage());
        }
    }
}