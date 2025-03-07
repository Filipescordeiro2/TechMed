package com.br.TechMed.utils.validation;

import com.br.TechMed.Enum.StatusUsuario;
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
import com.br.TechMed.utils.utilitaria.ValidacaoResultado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class AgendaValidation {

    private final ProfissionaisClinicaRepository profissionaisClinicaRepository;
    private final EspecialidadeProfissionalRepository especialidadeProfissionalRepository;
    private final ProfissionalRepository profissionalRepository;
    private final AgendaRepository agendaRepository;
    private final ClinicaRepository clinicaRepository;

    public void verificarVinculoProfissionalClinica(Long profissionalId, Long clinicaId) {
        boolean vinculoExiste = profissionaisClinicaRepository.existsByProfissionalIdAndClinicaEntityId(profissionalId, clinicaId);
        if (!vinculoExiste) {
            throw new RegraDeNegocioException("O profissional não está vinculado à clínica especificada.");
        }
    }

    public void verificarStatusAtivo(ProfissionalEntity profissional, ClinicaEntity clinica) {
        if (profissional.getStatusProfissional() != StatusUsuario.ATIVO) {
            throw new RegraDeNegocioException("O profissional não está ativo.");
        }
        if (clinica.getStatusClinica() != StatusUsuario.ATIVO) {
            throw new RegraDeNegocioException("A clínica não está ativa.");
        }
    }

    public void verificarConflitoDeAgenda(Long profissionalId, LocalDate data, LocalTime hora) {
        boolean existeConflito = agendaRepository.existsByProfissionalIdAndDataAndHora(profissionalId, data, hora);
        if (existeConflito) {
            throw new RegraDeNegocioException("Conflito de agenda: o profissional já possui um compromisso nesse horário.");
        }
    }

    public AgendaEntity validarAgenda(Long agendaId) {
        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new RegraDeNegocioException("Agenda não encontrada"));
    }

    public ProfissionalEntity validarProfissional(Long profissionalId) {
        return profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));
    }

    public ClinicaEntity validarClinica(Long clinicaId) {
        return clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional não encontrado"));
    }

    public EspecialidadeProfissionalEntity validarEspProfissional(Long especialidadeId) {
        return especialidadeProfissionalRepository.findById(especialidadeId)
                .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada"));
    }

    public ValidacaoResultado validarProfissionalEClinica(Long profissionalId, Long clinicaId, Long especialidadeId) {
        verificarVinculoProfissionalClinica(profissionalId, clinicaId);
        ProfissionalEntity profissional = validarProfissional(profissionalId);
        ClinicaEntity clinica = validarClinica(clinicaId);
        EspecialidadeProfissionalEntity especialidade = validarEspProfissional(especialidadeId);
        verificarStatusAtivo(profissional, clinica);
        return new ValidacaoResultado(profissional, clinica, especialidade);
    }
}
