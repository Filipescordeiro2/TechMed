package com.br.TechMed.dto.response.ProntuarioMedico;

import lombok.Builder;

@Builder
public record ProcedimentosClienteResponse(Long id, Long protuarioMedicoId, String procedimento) {
}
