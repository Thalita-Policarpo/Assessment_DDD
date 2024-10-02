package com.infnet.PetFriends_Pedidos.controller;


import com.infnet.PetFriends_Pedidos.domain.Pedido;
import com.infnet.PetFriends_Pedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@Slf4j
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Criar novo pedido")
    @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso")
    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        try {
            Pedido pedidoCriado = pedidoService.criarPedido(pedido);
            log.info("Pedido criado com sucesso: {}", pedidoCriado);
            return ResponseEntity.ok(pedidoCriado);
        } catch (Exception e) {
            log.error("Erro ao criar pedido", e);
            return ResponseEntity.badRequest().body(null);
        }
    }
}