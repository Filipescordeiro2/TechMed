package com.br.TechMed.dto.response.Clinica;

import lombok.Builder;

@Builder
public record ProfissionaisClinicaResponse(Long id,
                                           Long clinicaId,
                                           Long profissionalId) {
}