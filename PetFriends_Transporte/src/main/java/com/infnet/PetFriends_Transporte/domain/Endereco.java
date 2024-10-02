package com.infnet.PetFriends_Transporte.domain;



import com.infnet.PetFriends_Transporte.valueobject.StatusEntrega;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private String cidade;
    private String estado;

    @Embedded
    private StatusEntrega statusEntrega;
}