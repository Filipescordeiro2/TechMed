package com.br.TechMed.dto.request.prontuarioMedico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtuarioMedicoRequest {
    private Long profissionalId;
    private Long clienteId;
    private Long clinicaId;
    private String descricao;
    private LocalDate dataConsulta;
    private List<ExamesClienteRequest> exames;
    private List<ProcedimentosClienteRequest> procedimentos;
    private List<MedicamentosClienteRequest> medicamentos;
    private List<String> observacoes;
    private String cpf;

}
