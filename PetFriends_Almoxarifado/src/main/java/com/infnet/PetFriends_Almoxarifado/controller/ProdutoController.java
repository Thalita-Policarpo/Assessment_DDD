package com.infnet.PetFriends_Almoxarifado.controller;

import com.infnet.PetFriends_Almoxarifado.domain.Produto;
import com.infnet.PetFriends_Almoxarifado.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Slf4j
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Criar novo produto")
    @ApiResponse(responseCode = "200", description = "Produto criado com sucesso")
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        try {
            Produto produtoCriado = produtoService.criarProduto(produto);
            log.info("Produto criado com sucesso: {}", produtoCriado);
            return ResponseEntity.ok(produtoCriado);
        } catch (Exception e) {
            log.error("Erro ao criar produto", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Atualizar produto")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
            log.info("Produto atualizado com sucesso: {}", produtoAtualizado);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar produto", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Listar todos os produtos")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodosProdutos() {
        try {
            List<Produto> produtos = produtoService.listarTodosProdutos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            log.error("Erro ao listar produtos", e);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @Operation(summary = "Buscar produto por ID")
    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso")
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        try {
            Produto produto = produtoService.buscarProdutoPorId(id);
            return ResponseEntity.ok(produto);
        } catch (Exception e) {
            log.error("Erro ao buscar produto por ID", e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Deletar produto")
    @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        try {
            produtoService.deletarProduto(id);
            log.info("Produto deletado com sucesso com ID: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Erro ao deletar produto", e);
            return ResponseEntity.badRequest().build();
        }
    }
}