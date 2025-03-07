package com.br.TechMed.service.agenda;

import com.br.TechMed.Enum.Jornada;
import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.dto.request.Agenda.AgendaRequest;
import com.br.TechMed.dto.response.Agenda.AgendaResponse;
import com.br.TechMed.entity.agenda.AgendaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.agenda.AgendaRepository;
import com.br.TechMed.utils.utilitaria.AgendaUtils;
import com.br.TechMed.utils.utilitaria.ValidacaoResultado;
import com.br.TechMed.utils.validation.AgendaValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final AgendaUtils agendaUtils;
    private final AgendaValidation validation;

    public List<AgendaResponse> gerarAgenda(AgendaRequest request) {
        ValidacaoResultado validacao = validation.validarProfissionalEClinica(request.getProfissionalId(), request.getClinicaId(), request.getEspecialidadeId());
        List<AgendaEntity> agendaEntities = new ArrayList<>();
        LocalTime inicioManha = LocalTime.of(9, 0);
        LocalTime fimManha = LocalTime.of(12, 0);
        agendaEntities.addAll(agendaUtils.gerarHorarios(validacao.getProfissional(),
                validacao.getClinica(), validacao.getEspecialidade(),
                request.getData(), inicioManha, fimManha, 30, Jornada.MANHA));

        LocalTime inicioTarde = LocalTime.of(13, 0);
        LocalTime fimTarde = LocalTime.of(19, 0);
        agendaEntities.addAll(agendaUtils.gerarHorarios(validacao.getProfissional(),
                validacao.getClinica(), validacao.getEspecialidade(),
                request.getData(), inicioTarde, fimTarde, 30, Jornada.TARDE));

        LocalTime inicioNoite = LocalTime.of(19, 0);
        LocalTime fimNoite = LocalTime.of(22, 0);
        agendaEntities.addAll(agendaUtils.gerarHorarios(validacao.getProfissional(),
                validacao.getClinica(), validacao.getEspecialidade(), request.getData(), inicioNoite, fimNoite, 30, Jornada.NOITE));

        agendaRepository.saveAll(agendaEntities);
        return agendaUtils.convertToResponse(agendaEntities);
    }

    public List<AgendaResponse> gerarAgendaDaManha(AgendaRequest request) {
        ValidacaoResultado validacao = validation.validarProfissionalEClinica(request.getProfissionalId(), request.getClinicaId(), request.getEspecialidadeId());
        List<AgendaEntity> agendaEntities = new ArrayList<>();
        LocalTime inicioManha = LocalTime.of(9, 0);
        LocalTime fimManha = LocalTime.of(12, 0);
        agendaEntities.addAll(agendaUtils.gerarHorarios(validacao.getProfissional(),
                validacao.getClinica(), validacao.getEspecialidade(),
                request.getData(), inicioManha, fimManha, 30, Jornada.MANHA));
        agendaRepository.saveAll(agendaEntities);
        return agendaUtils.convertToResponse(agendaEntities);
    }

    public List<AgendaResponse> gerarAgendaDaTarde(AgendaRequest request) {
        ValidacaoResultado validacao = validation.validarProfissionalEClinica(request.getProfissionalId(), request.getClinicaId(), request.getEspecialidadeId());
        List<AgendaEntity> agendaEntities = new ArrayList<>();
        LocalTime inicioTarde = LocalTime.of(13, 0);
        LocalTime fimTarde = LocalTime.of(19, 0);
        agendaEntities.addAll(agendaUtils.gerarHorarios(validacao.getProfissional(),
                validacao.getClinica(), validacao.getEspecialidade(),
                request.getData(), inicioTarde, fimTarde, 30, Jornada.TARDE));
        agendaRepository.saveAll(agendaEntities);
        return agendaUtils.convertToResponse(agendaEntities);
    }

    public List<AgendaResponse> gerarAgendaDaNoite(AgendaRequest request) {
        ValidacaoResultado validacao = validation.validarProfissionalEClinica(request.getProfissionalId(), request.getClinicaId(), request.getEspecialidadeId());
        List<AgendaEntity> agendaEntities = new ArrayList<>();
        LocalTime inicioNoite = LocalTime.of(19, 0);
        LocalTime fimNoite = LocalTime.of(22, 0);
        agendaEntities.addAll(agendaUtils.gerarHorarios(validacao.getProfissional(),
                validacao.getClinica(), validacao.getEspecialidade(),
                request.getData(), inicioNoite, fimNoite, 30, Jornada.NOITE));
        agendaRepository.saveAll(agendaEntities);
        return agendaUtils.convertToResponse(agendaEntities);
    }

    public void criarAgendaAvulso(AgendaRequest request) {
        ValidacaoResultado validacao = validation.validarProfissionalEClinica(request.getProfissionalId(), request.getClinicaId(), request.getEspecialidadeId());
        validation.verificarConflitoDeAgenda(request.getProfissionalId(), request.getData(), request.getHora());
        Jornada jornada;
        if (request.getHora().isBefore(LocalTime.of(12, 0))) {
            jornada = Jornada.MANHA;
        } else if (request.getHora().isBefore(LocalTime.of(19, 0))) {
            jornada = Jornada.TARDE;
        } else {
            jornada = Jornada.NOITE;
        }
        AgendaEntity agenda = AgendaEntity.builder()
                .profissional(validacao.getProfissional())
                .clinicaEntity(validacao.getClinica())
                .especialidadeProfissionalEntity(validacao.getEspecialidade())
                .data(request.getData())
                .hora(request.getHora())
                .jornada(jornada)
                .statusAgenda(StatusAgenda.ABERTO)
                .build();
        agendaRepository.save(agenda);
    }

    @Transactional
    public void cancelarAgenda(Long agendaId) {
        var agenda = validation.validarAgenda(agendaId);
        if (agenda.getStatusAgenda() == StatusAgenda.CANCELADO) {
            throw new RegraDeNegocioException("A agenda já está cancelada");
        }
        agenda.setStatusAgenda(StatusAgenda.CANCELADO);
        agendaRepository.save(agenda);
    }

    public List<AgendaResponse> buscarAgendaPorStatus(StatusAgenda status) {
        List<AgendaEntity> agendaList = agendaRepository.findByStatusAgenda(status);
        return agendaList.stream().map(agenda ->
                AgendaResponse.builder()
                        .hora(agenda.getHora())
                        .data(agenda.getData())
                        .jornada(agenda.getJornada())
                        .statusAgenda(agenda.getStatusAgenda())
                        .build()
        ).collect(Collectors.toList());
    }
}