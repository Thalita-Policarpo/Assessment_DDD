package com.infnet.PetFriends_Pedidos.repository;


import com.infnet.PetFriends_Pedidos.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}