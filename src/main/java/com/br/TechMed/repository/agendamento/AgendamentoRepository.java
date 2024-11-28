package com.br.TechMed.repository.agendamento;

import com.br.TechMed.entity.agenda.AgendaEntity;
import com.br.TechMed.entity.agendamento.AgendamentoEntity;
import com.br.TechMed.entity.cliente.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity,Long> {

    Optional<AgendamentoEntity> findByAgenda(AgendaEntity agenda);
    List<AgendamentoEntity> findByCliente(ClienteEntity cliente);
}
