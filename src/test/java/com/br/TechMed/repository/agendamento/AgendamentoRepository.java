package com.br.TechMed.repository.agendamento;

import com.br.TechMed.entity.agendamento.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity,Long> {
}
