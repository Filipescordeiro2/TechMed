package com.br.TechMed.dto.agendamento;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AgendamentoDetalhadaDTO {

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
    private Long codigoCliente;
    private String nomeCliente;
    private String sobrenomeCliente;
    private String emailCliente;
    private String celularCliente;
    private String cpfCliente;
}
