package com.br.clinicaSantana.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "especialidade_clinica")
public class EspecialidadeClinicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome da especialidade é obrigatório")
    @Column(name = "nome_especialidade")
    private String nomeEspecialidade;

    @NotNull(message = "A descrição da especialidade é obrigatória")
    @Column(name = "descricao_especialidade")
    private String descricaoEspecialidade;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    @NotNull(message = "O cliente é obrigatório")
    private ClinicaEntity clinicaEntity;
}
