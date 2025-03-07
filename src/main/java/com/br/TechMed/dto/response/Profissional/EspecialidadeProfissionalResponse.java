package com.br.TechMed.dto.response.Profissional;

import com.br.TechMed.Enum.Especialidades;
import lombok.Builder;

@Builder
public record EspecialidadeProfissionalResponse(Especialidades especialidades) {
}
