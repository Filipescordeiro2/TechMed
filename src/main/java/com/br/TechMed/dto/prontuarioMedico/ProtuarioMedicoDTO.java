package com.br.TechMed.dto.prontuarioMedico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtuarioMedicoDTO {
    private Long id;
    private Long profissionalId;
    private Long clienteId; // Adicione este campo
    private Long clinicaId;
    private String descricao;
    private LocalDate dataConsulta;
    private List<ExamesClienteDTO> exames;
    private List<ProcedimentosClienteDTO> procedimentos;
    private List<MedicamentosClienteDTO> medicamentos;
    private List<String> observacoes;
    private String cpf;


}