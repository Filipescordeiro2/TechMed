package com.br.TechMed.dto.request.Profissional;

import com.br.TechMed.Enum.Especialidades;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadeProfissionalRequest {

    @NotNull(message = "O nome da especialidade é obrigatório")
    private Especialidades especialidades;

    public String getDescricaoEspecialidade() {
        return Especialidades.getDescricao(this.especialidades);
    }
}
