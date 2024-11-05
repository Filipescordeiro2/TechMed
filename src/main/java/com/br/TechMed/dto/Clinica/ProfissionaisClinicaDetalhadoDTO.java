package com.br.TechMed.dto.Clinica;

import lombok.Data;

@Data
public class ProfissionaisClinicaDetalhadoDTO {

    private Long id;
    private Long clinicaId;
    private Long profissionalId;
    private String nomeclinica;
    private String nomeProfissional;
}
