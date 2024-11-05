package com.br.TechMed.dto.agendamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDTO {

    private Long id;
    private Long agendaId;
    private Long clienteId;
}
