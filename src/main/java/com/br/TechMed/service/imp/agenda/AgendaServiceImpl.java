package com.br.TechMed.service.imp.agenda;

import com.br.TechMed.Enum.Jornada;
import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.dto.agenda.AgendaDTO;
import com.br.TechMed.dto.agenda.AgendaDetalhadaDTO;
import com.br.TechMed.entity.agenda.AgendaEntity;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.agenda.AgendaRepository;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.clinica.ProfissionaisClinicaRepository;
import com.br.TechMed.repository.profissional.EspecialidadeProfissionalRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import com.br.TechMed.service.servicos.agenda.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private ProfissionaisClinicaRepository profissionaisClinicaRepository;

    private void verificarVinculoProfissionalClinica(Long profissionalId, Long clinicaId) {
        boolean vinculoExiste = profissionaisClinicaRepository.existsByProfissionalIdAndClinicaEntityId(profissionalId, clinicaId);
        if (!vinculoExiste) {
            throw new RegraDeNegocioException("O profissional não está vinculado à clínica especificada.");
        }
    }

    private void verificarStatusAtivo(ProfissionalEntity profissional, ClinicaEntity clinica) {
        if (profissional.getStatusProfissional() != StatusUsuario.ATIVO) {
            throw new RegraDeNegocioException("O profissional não está ativo.");
        }
        if (clinica.getStatusClinica() != StatusUsuario.ATIVO) {
            throw new RegraDeNegocioException("A clínica não está ativa.");
        }
    }

    private void verificarConflitoDeAgenda(Long profissionalId, LocalDate data, LocalTime hora) {
        boolean existeConflito = agendaRepository.existsByProfissionalIdAndDataAndHora(profissionalId, data, hora);
        if (existeConflito) {
            throw new RegraDeNegocioException("Conflito de agenda: o profissional já possui um compromisso nesse horário.");
        }
    }

    private void salvarHorarios(List<AgendaDTO> agendaDTOList) {
        List<AgendaEntity> agendaList = agendaDTOList.stream().map(dto -> {
            ProfissionalEntity profissional = profissionalRepository.findById(dto.getProfissionalId())
                    .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

            ClinicaEntity clinica = clinicaRepository.findById(dto.getClinicaId())
                    .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

            verificarStatusAtivo(profissional, clinica);

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
    public List<AgendaDTO> gerarAgenda(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId) {
        verificarVinculoProfissionalClinica(profissionalId, clinicaId);

        ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

        verificarStatusAtivo(profissional, clinica);

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
        return agendaDTOList;
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
    public List<AgendaDTO> gerarAgendaDaManha(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId) {
        verificarVinculoProfissionalClinica(profissionalId, clinicaId);

        ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

        verificarStatusAtivo(profissional, clinica);

        EspecialidadeProfissionalEntity especialidade = especialidadeProfissionalRepository.findById(especialidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada"));

        List<AgendaDTO> agendaDTOList = new ArrayList<>();

        LocalTime inicioManha = LocalTime.of(9, 0);
        LocalTime fimManha = LocalTime.of(12, 0);
        agendaDTOList.addAll(gerarHorarios(profissional, clinica, especialidade, data, inicioManha, fimManha, 30, Jornada.MANHA));

        salvarHorarios(agendaDTOList);
        return agendaDTOList;
    }

    @Override
    public List<AgendaDTO> gerarAgendaDaTarde(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId) {
        verificarVinculoProfissionalClinica(profissionalId, clinicaId);

        ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

        verificarStatusAtivo(profissional, clinica);

        EspecialidadeProfissionalEntity especialidade = especialidadeProfissionalRepository.findById(especialidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada"));

        List<AgendaDTO> agendaDTOList = new ArrayList<>();

        LocalTime inicioTarde = LocalTime.of(13, 0);
        LocalTime fimTarde = LocalTime.of(19, 0);
        agendaDTOList.addAll(gerarHorarios(profissional, clinica, especialidade, data, inicioTarde, fimTarde, 30, Jornada.TARDE));

        salvarHorarios(agendaDTOList);
        return agendaDTOList;
    }

    @Override
    public List<AgendaDTO> gerarAgendaDaNoite(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId) {
        verificarVinculoProfissionalClinica(profissionalId, clinicaId);

        ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

        verificarStatusAtivo(profissional, clinica);

        EspecialidadeProfissionalEntity especialidade = especialidadeProfissionalRepository.findById(especialidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada"));

        List<AgendaDTO> agendaDTOList = new ArrayList<>();

        LocalTime inicioNoite = LocalTime.of(19, 0);
        LocalTime fimNoite = LocalTime.of(22, 0);
        agendaDTOList.addAll(gerarHorarios(profissional, clinica, especialidade, data, inicioNoite, fimNoite, 30, Jornada.NOITE));

        salvarHorarios(agendaDTOList);
        return agendaDTOList;
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
        verificarVinculoProfissionalClinica(profissionalId, clinicaId);

        ProfissionalEntity profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));

        verificarStatusAtivo(profissional, clinica);

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

    @Override
    @Transactional
    public List<AgendaDetalhadaDTO> buscarAgenda(Long profissionalId, Long clinicaId, String statusAgenda, LocalDate data, LocalTime hora, String nomeProfissional, Jornada periodo) {
        if (clinicaId == null) {
            throw new IllegalArgumentException("O ID da clínica não pode ser nulo");
        }

        List<AgendaEntity> agendas;
        if (profissionalId != null) {
            if (!profissionalRepository.existsById(profissionalId)) {
                throw new RegraDeNegocioException("Profissional não encontrado");
            }
            agendas = agendaRepository.findByProfissionalId(profissionalId);
        } else {
            agendas = agendaRepository.findByClinicaEntityId(clinicaId);
        }

        return agendas.stream()
                .filter(agenda -> agenda.getStatusAgenda() == StatusAgenda.ABERTO)
                .filter(agenda -> data == null || agenda.getData().equals(data))
                .filter(agenda -> hora == null || agenda.getHora().equals(hora))
                .filter(agenda -> nomeProfissional == null || agenda.getProfissional().getNome().equalsIgnoreCase(nomeProfissional))
                .filter(agenda -> periodo == null || agenda.getJornada() == periodo)
                .map(agenda -> {
                    AgendaDetalhadaDTO dto = new AgendaDetalhadaDTO();
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

                    return dto;
                }).collect(Collectors.toList());
    }
}