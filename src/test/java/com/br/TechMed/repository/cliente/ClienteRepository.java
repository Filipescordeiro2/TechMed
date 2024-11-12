package com.br.TechMed.repository.cliente;

import com.br.TechMed.entity.cliente.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório responsável por gerenciar as operações de persistência relacionadas à entidade ClienteEntity.
 */
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    /**
     * Busca um cliente pelo CPF.
     *
     * @param cpf CPF do cliente.
     * @return Um Optional contendo o cliente, se encontrado.
     */
    Optional<ClienteEntity> findByCpf(String cpf);

    /**
     * Busca um cliente pelo login.
     *
     * @param login Login do cliente.
     * @return Um Optional contendo o cliente, se encontrado.
     */
    Optional<ClienteEntity> findByLogin(String login);
}