package com.br.TechMed.dto.prontuarioMedico;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProtuarioMedicoDetalhadoDTO {

    private Long id;
    private Long profissionalId;
    private String profissionalNome;
    private String profissionalSobrenome;
    private Long clienteId;
    private String clienteNome;
    private String clienteSobrenome;
    private Long clinicaId;
    private String clinicaNome;
    private Integer idade;
    private LocalDate dataNascimento;
    private String cpf;
    private String descricao;
    private LocalDate dataConsulta;
    private List<ExamesClienteDTO> exames;
    private List<ProcedimentosClienteDTO> procedimentos;
    private List<MedicamentosClienteDTO> medicamentos;
    private String observacoes;
    private String numeroRegistro;
    private String orgaoRegulador;
    private String ufRegistro;


}
