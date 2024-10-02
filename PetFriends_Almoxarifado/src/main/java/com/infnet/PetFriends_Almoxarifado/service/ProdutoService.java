package com.infnet.PetFriends_Almoxarifado.service;

import com.infnet.PetFriends_Almoxarifado.domain.Produto;
import com.infnet.PetFriends_Almoxarifado.dto.AtualizarEstoqueMessage;
import com.infnet.PetFriends_Almoxarifado.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criarProduto(Produto produto) {
        log.info("Criando produto: {}", produto);
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, Produto produto) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setQuantidadeEstoque(produto.getQuantidadeEstoque());

        log.info("Atualizando produto: {}", produtoExistente);
        return produtoRepository.save(produtoExistente);
    }

    public List<Produto> listarTodosProdutos() {
        log.info("Listando todos os produtos");
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoPorId(Long id) {
        log.info("Buscando produto por ID: {}", id);
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void deletarProduto(Long id) {
        log.info("Deletando produto com ID: {}", id);
        produtoRepository.deleteById(id);
    }

    @RabbitListener(queues = "atualizarEstoqueQueue")
    public void atualizarEstoque(@Payload AtualizarEstoqueMessage atualizarEstoqueMessage) {
        log.info("Recebendo mensagem para atualizar estoque: {}", atualizarEstoqueMessage);

        Optional<Produto> produtoOpt = produtoRepository.findById(atualizarEstoqueMessage.getProdutoId());
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            int quantidadeAtualizada = produto.getQuantidadeEstoque() - atualizarEstoqueMessage.getQuantidadeSolicitada();

            if (quantidadeAtualizada < 0) {
                log.error("Quantidade em estoque insuficiente para o produto com ID: {}. Estoque atual: {}, solicitado: {}",
                        produto.getId(), produto.getQuantidadeEstoque(), atualizarEstoqueMessage.getQuantidadeSolicitada());
            } else {
                produto.setQuantidadeEstoque(quantidadeAtualizada);
                produtoRepository.save(produto);
                log.info("Estoque atualizado para o produto {}. Quantidade restante: {}", produto.getNome(), produto.getQuantidadeEstoque());
            }
        } else {
            log.error("Produto não encontrado para o ID: {}. Ignorando a atualização do estoque.", atualizarEstoqueMessage.getProdutoId());
        }
    }
}