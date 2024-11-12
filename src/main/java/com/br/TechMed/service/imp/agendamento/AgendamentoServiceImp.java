package com.br.TechMed.service.imp.agendamento;

import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.dto.agendamento.AgendamentoDTO;
import com.br.TechMed.entity.agenda.AgendaEntity;
import com.br.TechMed.entity.agendamento.AgendamentoEntity;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.agenda.AgendaRepository;
import com.br.TechMed.repository.agendamento.AgendamentoRepository;
import com.br.TechMed.repository.cliente.ClienteRepository;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.clinica.ProfissionaisClinicaRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import com.br.TechMed.service.servicos.agendamento.AgendamentoService;
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
        ClienteEntity cliente = validateCliente(agendamentoDTO.getClienteId());

        validateAgendaStatus(agenda);
        validateProfissionalStatus(agenda.getProfissional());
        validateClinicaStatus(agenda.getClinicaEntity());

        AgendamentoEntity agendamentoEntity = new AgendamentoEntity();
        agendamentoEntity.setAgenda(agenda);
        agendamentoEntity.setCliente(cliente);

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


    private ClienteEntity validateCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    private ClinicaEntity validateClinica(Long clinicaId) {
        return clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));
    }


    private void validateAgendaStatus(AgendaEntity agenda) {
        if (!agenda.getStatusAgenda().equals(StatusAgenda.ABERTO)) {
            throw new RegraDeNegocioException("A agenda não está aberta para agendamento");
        }
    }

    private void validateProfissionalStatus(ProfissionalEntity profissional) {
        if (profissional.getStatusProfissional() != StatusUsuario.ATIVO) {
            throw new RegraDeNegocioException("O profissional não está ativo.");
        }
    }

    private void validateClinicaStatus(ClinicaEntity clinica) {
        if (clinica.getStatusClinica() != StatusUsuario.ATIVO) {
            throw new RegraDeNegocioException("A clínica não está ativa.");
        }
    }
}