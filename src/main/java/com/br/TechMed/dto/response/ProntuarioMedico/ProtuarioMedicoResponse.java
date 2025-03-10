package com.br.TechMed.dto.response.ProntuarioMedico;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ProtuarioMedicoResponse(Long id,
                                      Long profissionalId,
                                      Long clienteId,
                                      Long clinicaId,
                                      String descricao,
                                      LocalDate dataConsulta,
                                      List<ExamesClienteResponse> exames,
                                      List<ProcedimentosClienteResponse> procedimentos,
                                      List<MedicamentosClienteResponse> medicamentos,
                                      List<String> observacoes, String cpf) {
}