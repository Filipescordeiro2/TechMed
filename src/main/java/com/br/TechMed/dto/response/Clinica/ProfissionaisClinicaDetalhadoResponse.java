package com.br.TechMed.dto.response.Clinica;

import lombok.Builder;

@Builder
public record ProfissionaisClinicaDetalhadoResponse(Long id,
                                                    Long clinicaId,
                                                    Long profissionalId,
                                                    String nomeclinica,
                                                    String nomeProfissional) {
}
