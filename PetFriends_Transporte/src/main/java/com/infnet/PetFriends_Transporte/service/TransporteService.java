package com.infnet.PetFriends_Transporte.service;

import com.infnet.PetFriends_Transporte.domain.Endereco;
import com.infnet.PetFriends_Transporte.repository.EnderecoRepository;
import com.infnet.PetFriends_Transporte.valueobject.StatusEntrega;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransporteService {
    private final EnderecoRepository enderecoRepository;

    public TransporteService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco criarEndereco(Endereco endereco) {
        log.info("Criando endereço: {}", endereco);
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarTodosEnderecos() {
        log.info("Listando todos os endereços");
        return enderecoRepository.findAll();
    }

    @RabbitListener(queues = "novaEntregaQueue")
    public void atualizarStatusEntrega(Long enderecoId) {
        log.info("Recebendo mensagem para atualizar status de entrega para o endereço ID: {}", enderecoId);
        try {
            Optional<Endereco> enderecoOpt = enderecoRepository.findById(enderecoId);
            if (enderecoOpt.isEmpty()) {
                log.error("Endereço não encontrado para o ID: {}. Ignorando a atualização do status de entrega.", enderecoId);
                return;
            }

            Endereco endereco = enderecoOpt.get();
            StatusEntrega statusEntrega = new StatusEntrega("Em Trânsito");
            endereco.setStatusEntrega(statusEntrega);
            enderecoRepository.save(endereco);

            log.info("Status de entrega atualizado para 'Em Trânsito' para o endereço: {}", endereco);
        } catch (Exception e) {
            log.error("Erro ao atualizar status de entrega para o endereço ID: {}", enderecoId, e);
        }
    }
}