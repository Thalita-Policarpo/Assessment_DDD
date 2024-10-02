package com.infnet.PetFriends_Almoxarifado.repository;


import com.infnet.PetFriends_Almoxarifado.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}