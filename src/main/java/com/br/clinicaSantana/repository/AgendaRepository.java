package com.br.clinicaSantana.repository;

import com.br.clinicaSantana.Enum.StatusAgenda;
import com.br.clinicaSantana.dto.AgendaDTO;
import com.br.clinicaSantana.entity.AgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Repositório responsável por gerenciar as operações de persistência relacionadas à entidade AgendaEntity.
 */
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {

    /**
     * Busca agendas pelo ID do profissional.
     *
     * @param profissionalId ID do profissional.
     * @return Lista de agendas do profissional.
     */
    List<AgendaEntity> findByProfissionalId(Long profissionalId);

    /**
     * Busca agendas pela data e hora.
     *
     * @param data Data da agenda.
     * @param hora Hora da agenda.
     * @return Lista de agendas na data e hora especificadas.
     */
    List<AgendaEntity> findByDataAndHora(LocalDate data, LocalTime hora);

    /**
     * Busca agendas pela data.
     *
     * @param data Data da agenda.
     * @return Lista de agendas na data especificada.
     */
    List<AgendaEntity> findByData(LocalDate data);

    /**
     * Busca agendas pela hora.
     *
     * @param hora Hora da agenda.
     * @return Lista de agendas na hora especificada.
     */
    List<AgendaEntity> findByHora(LocalTime hora);

    /**
     * Verifica se existe uma agenda para o profissional na data e hora especificadas.
     *
     * @param profissionalId ID do profissional.
     * @param data Data da agenda.
     * @param hora Hora da agenda.
     * @return true se existir uma agenda, false caso contrário.
     */
    boolean existsByProfissionalIdAndDataAndHora(Long profissionalId, LocalDate data, LocalTime hora);

    /**
     * Busca agendas pelo nome do profissional, ignorando maiúsculas e minúsculas.
     *
     * @param nome Nome do profissional.
     * @return Lista de agendas do profissional com o nome especificado.
     */
    List<AgendaEntity> findByProfissionalNomeContainingIgnoreCase(String nome);

    /**
     * Busca agendas pelo status.
     *
     * @param status Status da agenda.
     * @return Lista de agendas com o status especificado.
     */
    List<AgendaEntity> findByStatusAgenda(StatusAgenda status);

    @Query("SELECT new com.br.clinicaSantana.dto.AgendaDTO(a.id, a.profissional.id, a.clinicaEntity.id, a.especialidadeProfissionalEntity.id, a.hora, a.data, a.jornada, a.statusAgenda) " +
            "FROM AgendaEntity a " +
            "JOIN a.profissional p " +
            "JOIN a.clinicaEntity c " +
            "JOIN a.especialidadeProfissionalEntity e " +
            "WHERE p.id = :profissionalId")
    List<AgendaDTO> findAgendaByProfissional(Long profissionalId);
}