package com.br.clinicaSantana.service;

import com.br.clinicaSantana.dto.AgendamentoDTO;
import org.springframework.stereotype.Service;

@Service
public interface AgendamentoService {

    AgendamentoDTO criarAgendamento(AgendamentoDTO agendamentoDTO);

}
