package com.br.clinicaSantana.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa a relação entre profissionais e clínicas no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profissionais_clinica")
public class ProfissionaisClinicaEntity {

    /**
     * Identificador único da relação.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Profissional associado à clínica.
     */
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalEntity profissional;

    /**
     * Clínica associada ao profissional.
     */
    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private ClinicaEntity clinicaEntity;
}