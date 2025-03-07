package com.br.TechMed.service.imp.agendamento;

import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.dto.agendamento.AgendamentoDTO;
import com.br.TechMed.dto.agendamento.AgendamentoDetalhadaDTO;
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

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public List<AgendamentoDetalhadaDTO> getAgendamentoDetalhado(Long agendaId) {
        if (agendaId == null) {
            throw new IllegalArgumentException("O ID da agenda não pode ser nulo");
        }

        AgendaEntity agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new RegraDeNegocioException("Agenda não encontrada"));

        AgendamentoEntity agendamento = agendamentoRepository.findByAgenda(agenda)
                .orElseThrow(() -> new RegraDeNegocioException("Agendamento não encontrado para a agenda fornecida"));

        ClienteEntity cliente = agendamento.getCliente();

        AgendamentoDetalhadaDTO dto = new AgendamentoDetalhadaDTO();
        dto.setCodigoAgenda(agenda.getId());
        dto.setCodigoClinica(agenda.getClinicaEntity().getId());
        dto.setData(agenda.getData());
        dto.setHora(agenda.getHora());
        dto.setPeriodoAgenda(agenda.getJornada().name());
        dto.setStatusAgenda(agenda.getStatusAgenda().name());
        dto.setClinica(agenda.getClinicaEntity().getNomeClinica());
        dto.setEmailClinica(agenda.getClinicaEntity().getEmail());
        dto.setCelularClinica(agenda.getClinicaEntity().getCelular());
        dto.setNomeProfissional(agenda.getProfissional().getNome());
        dto.setNomeEspecialidadeProfissional(String.valueOf(agenda.getEspecialidadeProfissionalEntity().getEspecialidades()));
        dto.setDescricaoEspecialidadeProfissional(agenda.getEspecialidadeProfissionalEntity().getDescricaoEspecialidade());
        dto.setCodigoCliente(cliente.getId());
        dto.setNomeCliente(cliente.getNome());
        dto.setSobrenomeCliente(cliente.getSobrenome());
        dto.setEmailCliente(cliente.getEmail());
        dto.setCelularCliente(cliente.getCelular());
        dto.setCpfCliente(cliente.getCpf());

        return List.of(dto);
    }

    @Override
    @Transactional
    public List<AgendamentoDetalhadaDTO> getAgendamentoDetalhadoPorCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("O CPF do cliente não pode ser nulo ou vazio");
        }

        ClienteEntity cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));

        List<AgendamentoEntity> agendamentos = agendamentoRepository.findByCliente(cliente);

        if (agendamentos.isEmpty()) {
            throw new RegraDeNegocioException("Nenhum agendamento encontrado para o cliente fornecido");
        }

        return agendamentos.stream().map(agendamento -> {
            AgendaEntity agenda = agendamento.getAgenda();
            AgendamentoDetalhadaDTO dto = new AgendamentoDetalhadaDTO();
            dto.setCodigoAgenda(agenda.getId());
            dto.setCodigoClinica(agenda.getClinicaEntity().getId());
            dto.setData(agenda.getData());
            dto.setHora(agenda.getHora());
            dto.setPeriodoAgenda(agenda.getJornada().name());
            dto.setStatusAgenda(agenda.getStatusAgenda().name());
            dto.setClinica(agenda.getClinicaEntity().getNomeClinica());
            dto.setEmailClinica(agenda.getClinicaEntity().getEmail());
            dto.setCelularClinica(agenda.getClinicaEntity().getCelular());
            dto.setNomeProfissional(agenda.getProfissional().getNome());
            dto.setSobrenomeProfissional(agenda.getProfissional().getSobrenome());
            dto.setNomeEspecialidadeProfissional(String.valueOf(agenda.getEspecialidadeProfissionalEntity().getEspecialidades()));
            dto.setDescricaoEspecialidadeProfissional(agenda.getEspecialidadeProfissionalEntity().getDescricaoEspecialidade());
            dto.setCodigoCliente(cliente.getId());
            dto.setNomeCliente(cliente.getNome());
            dto.setSobrenomeCliente(cliente.getSobrenome());
            dto.setEmailCliente(cliente.getEmail());
            dto.setCelularCliente(cliente.getCelular());
            dto.setCpfCliente(cliente.getCpf());
            dto.setDataDeNascimentoCliente(cliente.getDataNascimento());
            return dto;
        }).collect(Collectors.toList());
    }
}