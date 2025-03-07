package com.br.TechMed.entity.agenda;

import com.br.TechMed.Enum.Jornada;
import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidade que representa uma agenda no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "agenda")
public class AgendaEntity {

    /**
     * Identificador único da agenda.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Profissional associado à agenda.
     */
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalEntity profissional;

    /**
     * Clínica associada à agenda.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinica_id", nullable = false)
    private ClinicaEntity clinicaEntity;

    /**
     * Especialidade do profissional associada à agenda.
     */
    @NotNull(message = "O ID da especialidade é obrigatório")
    @ManyToOne
    @JoinColumn(name = "especialidade_id")
    private EspecialidadeProfissionalEntity especialidadeProfissionalEntity;

    /**
     * Hora do agendamento.
     */
    private LocalTime hora;

    /**
     * Data do agendamento.
     */
    private LocalDate data;

    /**
     * Status da agenda.
     */
    @Enumerated(EnumType.STRING)
    private StatusAgenda statusAgenda;

    /**
     * Jornada de trabalho associada à agenda.
     */
    @Enumerated(EnumType.STRING)
    private Jornada jornada;
}