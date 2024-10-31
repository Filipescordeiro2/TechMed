package com.br.clinicaSantana.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agendamento")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agenda_id", nullable = false)
    private AgendaEntity agenda;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalEntity profissional;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    private ClinicaEntity clinica;
}