package com.br.TechMed.dto.response.ProntuarioMedico;

import lombok.Builder;

@Builder
public record MedicamentosClienteResponse(Long id, Long protuarioMedicoId, String medicamento) {
}
