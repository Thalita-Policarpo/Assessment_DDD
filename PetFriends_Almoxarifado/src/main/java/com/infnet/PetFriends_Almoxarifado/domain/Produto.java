package com.infnet.PetFriends_Almoxarifado.domain;

import com.infnet.PetFriends_Almoxarifado.valueobject.Preco;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Embedded
    private Preco preco;

    private int quantidadeEstoque;
}