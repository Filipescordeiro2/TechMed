package com.br.TechMed.service.servicos.agendamento;

import com.br.TechMed.dto.agendamento.AgendamentoDTO;
import com.br.TechMed.dto.agendamento.AgendamentoDetalhadaDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public interface AgendamentoService {

    AgendamentoDTO criarAgendamento(AgendamentoDTO agendamentoDTO);
    List<AgendamentoDetalhadaDTO> getAgendamentoDetalhado(Long agendaId);
    List<AgendamentoDetalhadaDTO> getAgendamentoDetalhadoPorCpf(String cpf);
}
