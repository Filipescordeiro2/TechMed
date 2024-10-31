package com.br.clinicaSantana.repository;

import com.br.clinicaSantana.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity,Long> {
}
