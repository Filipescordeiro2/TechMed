package com.br.TechMed.service.servicos.agendamento;

import com.br.TechMed.dto.agendamento.AgendamentoDTO;
import org.springframework.stereotype.Service;

@Service
public interface AgendamentoService {

    AgendamentoDTO criarAgendamento(AgendamentoDTO agendamentoDTO);

}
