package com.br.TechMed.dto.response.Profissional;

import com.br.TechMed.dto.response.Clinica.ProfissionaisClinicaResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ProfissionalRegisterResponse(String message,
                                           String nome,
                                           String sobrenome,
                                           String cpf,
                                           List<EspecialidadeProfissionalResponse> especialidadesProfisisonal,
                                           List<ProfissionaisClinicaResponse> profissionaisClinica) {
}
