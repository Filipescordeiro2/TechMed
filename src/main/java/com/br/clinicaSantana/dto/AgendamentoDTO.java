package com.br.clinicaSantana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDTO {

    private Long id;
    private Long agendaId;
    private Long profissionalId;
    private Long clienteId;
    private Long clinicaId;
}
