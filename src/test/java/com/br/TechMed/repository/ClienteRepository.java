package com.br.TechMed.repository;

import com.br.TechMed.entity.cliente.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity,Long> {
}
