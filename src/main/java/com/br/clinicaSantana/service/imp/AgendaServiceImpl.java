package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.Enum.Jornada;
import com.br.clinicaSantana.Enum.StatusAgenda;
import com.br.clinicaSantana.dto.AgendaDTO;
import com.br.clinicaSantana.entity.AgendaEntity;
import com.br.clinicaSantana.entity.ClinicaEntity;
import com.br.clinicaSantana.entity.EspecialidadeProfissionalEntity;
import com.br.clinicaSantana.entity.ProfissionalEntity;
import com.br.clinicaSantana.exception.RegraDeNegocioException;
import com.br.clinicaSantana.repository.AgendaRepository;
import com.br.clinicaSantana.repository.ClinicaRepository;
import com.br.clinicaSantana.repository.EspecialidadeProfissionalRepository;
import com.br.clinicaSantana.repository.ProfissionalRepository;
import com.br.clinicaSantana.service.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementação da interface AgendaService.
 * Fornece métodos para gerenciar agendas de profissionais.
 */
@Service
@RequiredArgsConstructor
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private EspecialidadeProfissionalRepository especialidadeProfissionalRepository;

    /**
     * Verifica se há um conflito de agendamento para um profissional em uma data e hora específicas.
     *
     * @param profissionalId o ID do profissional
     * @param data a data do compromisso
     * @param hora a hora do compromisso
     */
    private void verificarConflitoDeAgenda(Long profissionalId, LocalDate data, LocalTime hora) {
        boolean existeConflito = agendaRepository.existsByProfissionalIdAndDataAndHora(profissionalId, data, hora);
        if (existeConflito) {
            throw new RegraDeNegocioException("Conflito de agenda: o profissional já possui um compromisso nesse horário.");
        }
    }

    /**
     * Salva uma lista de DTOs de agenda no banco de dados.
     *
     * @param agendaDTOList a lista de DTOs de agenda a ser salva
     */
    private void salvarHorarios(List<AgendaDTO> agendaDTOList) {
        List<AgendaEntity> agendaList = agendaDTOList.stream().map(dto -> {
            ProfissionalEntity profissional = profissionalRepository.findById(dto.getProfissionalId())
                    .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

            ClinicaEntity clinica = clinicaRepository.findById(dto.getClinicaId())
                    .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

            EspecialidadeProfissionalEntity especialidade = especialidadeProfissionalRepository.findById(dto.getEspecialidadeId())
                    .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada"));

            verificarConflitoDeAgenda(dto.getProfissionalId(), dto.getData(), dto.getHora());

            AgendaEntity agenda = new AgendaEntity();
            agenda.setProfissional(profissional);
            agenda.setClinicaEntity(clinica);
            agenda.setEspecialidadeProfissionalEntity(especialidade);
            agenda.setData(dto.getData());
            agenda.setHora(dto.getHora());
            agenda.setJornada(dto.getJornada());
            agenda.setStatusAgenda(dto.getStatusAgenda());

            return agenda;
        }).collect(Collectors.toList());

        agendaRepository.saveAll(agendaList);
    }

    /**
     * Gera uma lista de DTOs de agenda para um intervalo de tempo e intervalo específicos.
     *
     * @param profissional a entidade do profissional
     * @param clinica a entidade da clínica
     * @param especialidade a entidade da especialidade
     * @param data a data dos compromissos
     * @param inicio a hora de início
     * @param fim a hora de término
     * @param intervaloMinutos o intervalo em minutos entre os compromissos
     * @param jornada o turno de trabalho
     * @return uma lista de DTOs de agenda gerados
     */
    private List<AgendaDTO> gerarHorarios(ProfissionalEntity profissional, ClinicaEntity clinica, EspecialidadeProfissionalEntity especialidade, LocalDate data, LocalTime inicio, LocalTime fim, int intervaloMinutos, Jornada jornada) {
        List<AgendaDTO> horarios = new ArrayList<>();
        LocalTime horarioAtual = inicio;

        while (horarioAtual.isBefore(fim)) {
            AgendaDTO dto = new AgendaDTO();
            dto.setProfissionalId(profissional.getId());
            dto.setClinicaId(clinica.getId());
            dto.setEspecialidadeId(especialidade.getId());
            dto.setData(data);
            dto.setHora(horarioAtual);
            dto.setJornada(jornada);
            dto.setStatusAgenda(StatusAgenda.ABERTO);

            horarios.add(dto);
            horarioAtual = horarioAtual.plusMinutes(intervaloMinutos);
        }

        return horarios;
    }

    @Override
    public void gerarAgenda(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId) {
        ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

        EspecialidadeProfissionalEntity especialidade = especialidadeProfissionalRepository.findById(especialidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada"));

        List<AgendaDTO> agendaDTOList = new ArrayList<>();

        LocalTime inicioManha = LocalTime.of(9, 0);
        LocalTime fimManha = LocalTime.of(12, 0);
        agendaDTOList.addAll(gerarHorarios(profissional, clinica, especialidade, data, inicioManha, fimManha, 30, Jornada.MANHA));

        LocalTime inicioTarde = LocalTime.of(13, 0);
        LocalTime fimTarde = LocalTime.of(19, 0);
        agendaDTOList.addAll(gerarHorarios(profissional, clinica, especialidade, data, inicioTarde, fimTarde, 30, Jornada.TARDE));

        LocalTime inicioNoite = LocalTime.of(19, 0);
        LocalTime fimNoite = LocalTime.of(22, 0);
        agendaDTOList.addAll(gerarHorarios(profissional, clinica, especialidade, data, inicioNoite, fimNoite, 30, Jornada.NOITE));

        salvarHorarios(agendaDTOList);
    }

    @Override
    public List<AgendaDTO> buscarAgendaPorProfissional(Long profissionalId) {
        List<AgendaEntity> agendaList = agendaRepository.findByProfissionalId(profissionalId);
        return agendaList.stream().map(agenda -> {
            AgendaDTO dto = new AgendaDTO();
            dto.setId(agenda.getId());
            dto.setProfissionalId(agenda.getProfissional().getId());
            dto.setClinicaId(agenda.getClinicaEntity().getId());
            dto.setEspecialidadeId(agenda.getEspecialidadeProfissionalEntity().getId());
            dto.setData(agenda.getData());
            dto.setHora(agenda.getHora());
            dto.setJornada(agenda.getJornada());
            dto.setStatusAgenda(agenda.getStatusAgenda());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void gerarAgendaDaManha(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId) {
        ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

        EspecialidadeProfissionalEntity especialidade = especialidadeProfissionalRepository.findById(especialidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada"));

        List<AgendaDTO> agendaDTOList = new ArrayList<>();

        LocalTime inicioManha = LocalTime.of(9, 0);
        LocalTime fimManha = LocalTime.of(12, 0);
        agendaDTOList.addAll(gerarHorarios(profissional, clinica, especialidade, data, inicioManha, fimManha, 30, Jornada.MANHA));

        salvarHorarios(agendaDTOList);
    }

    @Override
    public void gerarAgendaDaTarde(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId) {
        ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

        EspecialidadeProfissionalEntity especialidade = especialidadeProfissionalRepository.findById(especialidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada"));

        List<AgendaDTO> agendaDTOList = new ArrayList<>();

        LocalTime inicioTarde = LocalTime.of(13, 0);
        LocalTime fimTarde = LocalTime.of(19, 0);
        agendaDTOList.addAll(gerarHorarios(profissional, clinica, especialidade, data, inicioTarde, fimTarde, 30, Jornada.TARDE));

        salvarHorarios(agendaDTOList);
    }

    @Override
    public void gerarAgendaDaNoite(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId) {
        ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

        EspecialidadeProfissionalEntity especialidade = especialidadeProfissionalRepository.findById(especialidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada"));

        List<AgendaDTO> agendaDTOList = new ArrayList<>();

        LocalTime inicioNoite = LocalTime.of(19, 0);
        LocalTime fimNoite = LocalTime.of(22, 0);
        agendaDTOList.addAll(gerarHorarios(profissional, clinica, especialidade, data, inicioNoite, fimNoite, 30, Jornada.NOITE));

        salvarHorarios(agendaDTOList);
    }

    @Override
    public List<AgendaDTO> buscarAgenda(Optional<LocalDate> data, Optional<LocalTime> hora, Optional<String> nomeProfissional) {
        List<AgendaEntity> agendaList;

        if (nomeProfissional.isPresent()) {
            agendaList = agendaRepository.findByProfissionalNomeContainingIgnoreCase(nomeProfissional.get());
        } else if (data.isPresent() && hora.isPresent()) {
            agendaList = agendaRepository.findByDataAndHora(data.get(), hora.get());
        } else if (data.isPresent()) {
            agendaList = agendaRepository.findByData(data.get());
        } else if (hora.isPresent()) {
            agendaList = agendaRepository.findByHora(hora.get());
        } else {
            agendaList = agendaRepository.findAll();
        }

        return agendaList.stream().map(agenda -> {
            AgendaDTO dto = new AgendaDTO();
            dto.setId(agenda.getId());
            dto.setProfissionalId(agenda.getProfissional().getId());
            dto.setClinicaId(agenda.getClinicaEntity().getId());
            dto.setEspecialidadeId(agenda.getEspecialidadeProfissionalEntity().getId());
            dto.setData(agenda.getData());
            dto.setHora(agenda.getHora());
            dto.setJornada(agenda.getJornada());
            dto.setStatusAgenda(agenda.getStatusAgenda());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<AgendaDTO> buscarAgendaPorStatus(StatusAgenda status) {
        List<AgendaEntity> agendaList = agendaRepository.findByStatusAgenda(status);
        return agendaList.stream().map(agenda -> {
            AgendaDTO dto = new AgendaDTO();
            dto.setId(agenda.getId());
            dto.setProfissionalId(agenda.getProfissional().getId());
            dto.setClinicaId(agenda.getClinicaEntity().getId());
            dto.setEspecialidadeId(agenda.getEspecialidadeProfissionalEntity().getId());
            dto.setData(agenda.getData());
            dto.setHora(agenda.getHora());
            dto.setJornada(agenda.getJornada());
            dto.setStatusAgenda(agenda.getStatusAgenda());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void criarAgendaAvulso(Long profissionalId, LocalDate data, LocalTime hora, Long clinicaId, Long especialidadeId) {
        ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

        EspecialidadeProfissionalEntity especialidade = especialidadeProfissionalRepository.findById(especialidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada"));

        verificarConflitoDeAgenda(profissionalId, data, hora);

        Jornada jornada;
        if (hora.isBefore(LocalTime.of(12, 0))) {
            jornada = Jornada.MANHA;
        } else if (hora.isBefore(LocalTime.of(19, 0))) {
            jornada = Jornada.TARDE;
        } else {
            jornada = Jornada.NOITE;
        }

        AgendaEntity agenda = new AgendaEntity();
        agenda.setProfissional(profissional);
        agenda.setClinicaEntity(clinica);
        agenda.setEspecialidadeProfissionalEntity(especialidade);
        agenda.setData(data);
        agenda.setHora(hora);
        agenda.setJornada(jornada);
        agenda.setStatusAgenda(StatusAgenda.ABERTO);

        agendaRepository.save(agenda);
    }
}