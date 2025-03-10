package com.br.TechMed.dto.request.Agendamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoRequest {

    private Long agendaId;
    private Long clienteId;
}
