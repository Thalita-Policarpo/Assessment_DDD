package com.infnet.PetFriends_Transporte.repository;


import com.infnet.PetFriends_Transporte.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}