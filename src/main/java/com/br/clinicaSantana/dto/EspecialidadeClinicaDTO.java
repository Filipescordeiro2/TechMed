package com.br.clinicaSantana.dto;

import com.br.clinicaSantana.Enum.Especialidades;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadeClinicaDTO {

    private Long id;

    @NotNull(message = "O nome da especialidade é obrigatório")
    private Especialidades especialidades;

    public String getDescricaoEspecialidade() {
        return Especialidades.getDescricao(this.especialidades);
    }
}