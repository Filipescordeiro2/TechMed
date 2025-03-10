package com.br.TechMed.utils.validation;

import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.Enum.StatusUsuario;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AgendamentoValidation {

    private final AgendaRepository agendaRepository;

    private final ProfissionalRepository profissionalRepository;

    private final ClienteRepository clienteRepository;

    private final ProfissionaisClinicaRepository profissionaisClinicaRepository;

    private final ClinicaRepository clinicaRepository;

    private final AgendamentoRepository agendamentoRepository;


    public AgendaEntity validateAgenda(Long agendaId) {
        if (agendaId == null) {
            throw new IllegalArgumentException("O ID da agenda não pode ser nulo");
        }
        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new RegraDeNegocioException("Agenda não encontrada"));
    }

    public AgendamentoEntity validateAgendamento(AgendaEntity agenda) {
        var agendamento = agendamentoRepository.findByAgenda(agenda)
                .orElseThrow(() -> new RegraDeNegocioException("Agendamento não encontrado para a agenda fornecida"));

        return agendamento;
    }

    public ClienteEntity validateCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public ClienteEntity validateClienteCPF(String cpf){
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("O CPF do cliente não pode ser nulo ou vazio");
        }
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public ClinicaEntity validateClinica(Long clinicaId) {
        return clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));
    }


    public void validateAgendaStatus(AgendaEntity agenda) {
        if (!agenda.getStatusAgenda().equals(StatusAgenda.ABERTO)) {
            throw new RegraDeNegocioException("A agenda não está aberta para agendamento");
        }
    }

    public void validateProfissionalStatus(ProfissionalEntity profissional) {
        if (profissional.getStatusProfissional() != StatusUsuario.ATIVO) {
            throw new RegraDeNegocioException("O profissional não está ativo.");
        }
    }

    public void validateClinicaStatus(ClinicaEntity clinica) {
        if (clinica.getStatusClinica() != StatusUsuario.ATIVO) {
            throw new RegraDeNegocioException("A clínica não está ativa.");
        }
    }

    public List<AgendamentoEntity> validateAgendamentosByCliente(ClienteEntity cliente) {
        List<AgendamentoEntity> agendamentos = agendamentoRepository.findByCliente(cliente);
        if (agendamentos.isEmpty()) {
            throw new RegraDeNegocioException("Nenhum agendamento encontrado para o cliente fornecido");
        }
        return agendamentos;
    }

}
