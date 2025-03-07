package com.br.TechMed.dto.response.Clinica;

import com.br.TechMed.Enum.Especialidades;
import lombok.Builder;

@Builder
public record EspecialidadeClinicaResponse(Long id,
                                           Especialidades especialidades) {
}