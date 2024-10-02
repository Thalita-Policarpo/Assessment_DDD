package com.infnet.PetFriends_Transporte.valueobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusEntrega {
    String status; // Exemplo: "Pendente", "Em Trânsito", "Entregue"
}