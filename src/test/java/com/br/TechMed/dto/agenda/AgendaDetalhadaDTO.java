package com.br.TechMed.dto.agenda;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDetalhadaDTO {

    private Long codigoAgenda;
    private LocalDate data;
    private LocalTime hora;
    private String periodoAgenda;
    private String statusAgenda;
    private String clinica;
    private Long codigoClinica;
    private String emailClinica;
    private String celularClinica;
    private String nomeProfissional;
    private String nomeEspecialidadeProfissional;
    private String descricaoEspecialidadeProfissional;
}
