package com.infnet.PetFriends_Pedidos.dto;

import lombok.Data;

@Data
public class ProdutoEstoqueResponse {
    private Long id;
    private int quantidadeEstoque;
}