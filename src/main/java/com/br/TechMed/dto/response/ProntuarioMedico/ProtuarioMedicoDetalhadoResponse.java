package com.br.TechMed.dto.response.ProntuarioMedico;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ProtuarioMedicoDetalhadoResponse(Long id, Long profissionalId, String profissionalNome, String profissionalSobrenome, Long clienteId, String clienteNome, String clienteSobrenome, Long clinicaId, String clinicaNome, Integer idade, LocalDate dataNascimento, String cpf, String descricao, LocalDate dataConsulta, List<ExamesClienteResponse> exames, List<ProcedimentosClienteResponse> procedimentos, List<MedicamentosClienteResponse> medicamentos, String observacoes, String numeroRegistro, String orgaoRegulador, String ufRegistro) {
}