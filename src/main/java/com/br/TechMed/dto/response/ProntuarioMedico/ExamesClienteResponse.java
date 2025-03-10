package com.br.TechMed.dto.response.ProntuarioMedico;

import lombok.Builder;

@Builder
public record ExamesClienteResponse(Long id,Long protuarioMedicoId, String exame) {
}
