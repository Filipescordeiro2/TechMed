package com.br.TechMed.entity.profissional;

import com.br.TechMed.Enum.Especialidades;
import com.br.TechMed.dto.request.Clinica.EspecialidadeClinicaRequest;
import com.br.TechMed.dto.request.Profissional.EspecialidadeProfissionalRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa uma especialidade de profissional no sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "especialidade_profissional")
public class EspecialidadeProfissionalEntity {

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
    @Column(name = "nome_especialidade_profissional")
    @Enumerated(EnumType.STRING)
    private Especialidades especialidades;

    /**
     * Descrição da especialidade.
     */
    @NotNull(message = "A descrição da especialidade é obrigatória")
    @Column(name = "descricao_especialidade_profissional")
    private String descricaoEspecialidade;

    /**
     * Profissional associado à especialidade.
     */
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    @NotNull(message = "O profissional é obrigatório")
    private ProfissionalEntity profissionalEntity;

    /**
     * Método que define a descrição da especialidade antes de persistir ou atualizar.
     */
    @PrePersist
    @PreUpdate
    private void definirDescricao() {
        this.descricaoEspecialidade = Especialidades.getDescricao(this.especialidades);
    }

    public EspecialidadeProfissionalEntity(EspecialidadeProfissionalRequest request) {
        this.especialidades = request.getEspecialidades();
        this.descricaoEspecialidade = request.getDescricaoEspecialidade();
    }
}