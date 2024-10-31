package com.br.clinicaSantana.repository;

import com.br.clinicaSantana.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity,Long> {
}
