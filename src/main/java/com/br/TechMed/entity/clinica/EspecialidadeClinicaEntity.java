package com.br.TechMed.entity.clinica;

import com.br.TechMed.Enum.Especialidades;
import com.br.TechMed.dto.request.Clinica.EspecialidadeClinicaRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa uma especialidade de clínica no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "especialidade_clinica")
public class EspecialidadeClinicaEntity {

    /**
     * Identificador único da especialidade.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da especialidade.
     */
    @NotNull(message = "O nome da especialidade é obrigatório")
    @Column(name = "nome_especialidade")
    @Enumerated(EnumType.STRING)
    private Especialidades especialidades;

    /**
     * Descrição da especialidade.
     */
    @NotNull(message = "A descrição da especialidade é obrigatória")
    @Column(name = "descricao_especialidade")
    private String descricaoEspecialidade;

    /**
     * Clínica associada à especialidade.
     */
    @ManyToOne
    @JoinColumn(name = "clinica_id")
    @NotNull(message = "A clinica é obrigatório")
    private ClinicaEntity clinicaEntity;

    /**
     * Método que define a descrição da especialidade antes de persistir ou atualizar.
     */
    @PrePersist
    @PreUpdate
    private void definirDescricao() {
        this.descricaoEspecialidade = Especialidades.getDescricao(this.especialidades);
    }

    public EspecialidadeClinicaEntity(EspecialidadeClinicaRequest request) {
        this.especialidades = request.getEspecialidades();
        this.descricaoEspecialidade = request.getDescricaoEspecialidade();
    }


}