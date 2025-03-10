package com.br.TechMed.service.Agendamento;

import com.br.TechMed.Enum.StatusAgenda;

import com.br.TechMed.dto.request.Agendamento.AgendamentoRequest;
import com.br.TechMed.dto.response.Agendamento.AgendamentoDetalhadaResponse;
import com.br.TechMed.dto.response.Agendamento.AgendamentoReponse;
import com.br.TechMed.entity.agendamento.AgendamentoEntity;
import com.br.TechMed.repository.agenda.AgendaRepository;
import com.br.TechMed.repository.agendamento.AgendamentoRepository;
import com.br.TechMed.repository.cliente.ClienteRepository;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.clinica.ProfissionaisClinicaRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import com.br.TechMed.utils.utilitaria.AgendamentoUtils;
import com.br.TechMed.utils.validation.AgendamentoValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService  {


    private final AgendamentoRepository agendamentoRepository;
    private final AgendaRepository agendaRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionaisClinicaRepository profissionaisClinicaRepository;
    private final ClinicaRepository clinicaRepository;
    private final AgendamentoValidation agendamentoValidation;
    private final AgendamentoUtils agendamentoUtils;

    @Transactional
    public AgendamentoReponse criarAgendamento(AgendamentoRequest request) {
        var agenda = agendamentoValidation.validateAgenda(request.getAgendaId());
        var cliente = agendamentoValidation.validateCliente(request.getClienteId());

        agendamentoValidation.validateAgendaStatus(agenda);
        agendamentoValidation.validateProfissionalStatus(agenda.getProfissional());
        agendamentoValidation.validateClinicaStatus(agenda.getClinicaEntity());

        var agendamentoEntity = new AgendamentoEntity(request);
        agendamentoRepository.save(agendamentoEntity);
        agenda.setStatusAgenda(StatusAgenda.AGENDADO);
        agendaRepository.save(agenda);
        return agendamentoUtils.convertAgendamentoResponse(agendamentoEntity);
    }

    @Transactional
    public List<AgendamentoDetalhadaResponse> getAgendamentoDetalhado(Long agendaId) {
        var agenda = agendamentoValidation.validateAgenda(agendaId);
        var agendamento = agendamentoValidation.validateAgendamento(agenda);
        var cliente = agendamento.getCliente();
        return agendamentoUtils.createAgendamentoDetalhadaResponse(agenda, cliente);
    }

    @Transactional
    public List<AgendamentoDetalhadaResponse> getAgendamentoDetalhadoPorCpf(String cpf) {
        var cliente = agendamentoValidation.validateClienteCPF(cpf);
        var agendamentos = agendamentoValidation.validateAgendamentosByCliente(cliente);

        return agendamentos.stream()
                .map(agendamento -> agendamentoUtils.createAgendamentoDetalhadaResponse(agendamento.getAgenda(), cliente))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}