package com.infnet.PetFriends_Almoxarifado.dto;

import lombok.Data;

@Data
public class AtualizarEstoqueMessage {
    private Long produtoId;
    private int quantidadeSolicitada;
}