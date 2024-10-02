package com.infnet.PetFriends_Transporte.controller;

import com.infnet.PetFriends_Transporte.domain.Endereco;
import com.infnet.PetFriends_Transporte.service.TransporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@Slf4j
public class EnderecoController {
    private final TransporteService transporteService;

    public EnderecoController(TransporteService transporteService) {
        this.transporteService = transporteService;
    }

    @Operation(summary = "Criar novo endereço")
    @ApiResponse(responseCode = "200", description = "Endereço criado com sucesso")
    @PostMapping
    public ResponseEntity<Endereco> criarEndereco(@RequestBody Endereco endereco) {
        try {
            Endereco enderecoCriado = transporteService.criarEndereco(endereco);
            log.info("Endereço criado com sucesso: {}", enderecoCriado);
            return ResponseEntity.ok(enderecoCriado);
        } catch (Exception e) {
            log.error("Erro ao criar endereço", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Listar todos os endereços")
    @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Endereco>> listarEnderecos() {
        List<Endereco> enderecos = transporteService.listarTodosEnderecos();
        return ResponseEntity.ok(enderecos);
    }
}
