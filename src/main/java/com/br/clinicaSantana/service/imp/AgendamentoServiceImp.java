package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.Enum.StatusAgenda;
import com.br.clinicaSantana.dto.AgendamentoDTO;
import com.br.clinicaSantana.entity.*;
import com.br.clinicaSantana.exception.RegraDeNegocioException;
import com.br.clinicaSantana.repository.*;
import com.br.clinicaSantana.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgendamentoServiceImp implements AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProfissionaisClinicaRepository profissionaisClinicaRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Override
    @Transactional
    public AgendamentoDTO criarAgendamento(AgendamentoDTO agendamentoDTO) {
        AgendaEntity agenda = validateAgenda(agendamentoDTO.getAgendaId());
        ProfissionalEntity profissional = validateProfissional(agendamentoDTO.getProfissionalId());
        ClienteEntity cliente = validateCliente(agendamentoDTO.getClienteId());
        ClinicaEntity clinica = validateClinica(agendamentoDTO.getClinicaId());

        validateProfissionalClinica(agendamentoDTO.getProfissionalId(), agendamentoDTO.getClinicaId());
        validateClinicaAgenda(agenda, agendamentoDTO.getClinicaId());
        validateAgendaStatus(agenda);

        AgendamentoEntity agendamentoEntity = new AgendamentoEntity();
        agendamentoEntity.setAgenda(agenda);
        agendamentoEntity.setProfissional(profissional);
        agendamentoEntity.setCliente(cliente);
        agendamentoEntity.setClinica(clinica);

        agendamentoEntity = agendamentoRepository.save(agendamentoEntity);

        // Atualizar o status da agenda para AGENDADO
        agenda.setStatusAgenda(StatusAgenda.AGENDADO);
        agendaRepository.save(agenda);

        agendamentoDTO.setId(agendamentoEntity.getId());
        return agendamentoDTO;
    }

    private AgendaEntity validateAgenda(Long agendaId) {
        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new RegraDeNegocioException("Agenda não encontrada"));
    }

    private ProfissionalEntity validateProfissional(Long profissionalId) {
        return profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));
    }

    private ClienteEntity validateCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    private ClinicaEntity validateClinica(Long clinicaId) {
        return clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));
    }

    private void validateProfissionalClinica(Long profissionalId, Long clinicaId) {
        profissionaisClinicaRepository.findByProfissionalIdAndClinicaEntityId(profissionalId, clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não está vinculado à clínica"));
    }

    private void validateClinicaAgenda(AgendaEntity agenda, Long clinicaId) {
        if (!agenda.getClinicaEntity().getId().equals(clinicaId)) {
            throw new RegraDeNegocioException("Clínica da agenda não corresponde à clínica do agendamento");
        }
    }

    private void validateAgendaStatus(AgendaEntity agenda) {
        if (!agenda.getStatusAgenda().equals(StatusAgenda.ABERTO)) {
            throw new RegraDeNegocioException("A agenda não está aberta para agendamento");
        }
    }
}