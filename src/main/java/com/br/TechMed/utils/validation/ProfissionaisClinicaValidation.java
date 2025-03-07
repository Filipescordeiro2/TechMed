package com.br.TechMed.utils.validation;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.dto.request.Clinica.ProfissionaisClinicaRequest;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.clinica.ProfissionaisClinicaRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfissionaisClinicaValidation {

    private final ProfissionaisClinicaRepository profissionaisClinicaRepository;
    private final ClinicaRepository clinicaRepository;
    private final ProfissionalRepository profissionalRepository;

    public void validateProfissionaisClinicaRequest(ProfissionaisClinicaRequest request) {
        if (profissionaisClinicaRepository.existsByClinicaEntityIdAndProfissionalId(
                request.getClinicaId(), request.getProfissionalId())) {
            throw new RegraDeNegocioException("Já existe um profissional com este ID para a clínica especificada.");
        }

        if (!isClinicaAtiva(request.getClinicaId())) {
            throw new RegraDeNegocioException("A clínica especificada não está ativa.");
        }

        if (!isProfissionalAtivo(request.getProfissionalId())) {
            throw new RegraDeNegocioException("O profissional especificado não está ativo.");
        }
    }

    private boolean isClinicaAtiva(Long clinicaId) {
        ClinicaEntity clinica = fetchClinicaById(clinicaId);
        return clinica.getStatusClinica() == StatusUsuario.ATIVO;
    }

    private boolean isProfissionalAtivo(Long profissionalId) {
        ProfissionalEntity profissional = fetchProfissionalById(profissionalId);
        return profissional.getStatusProfissional() == StatusUsuario.ATIVO;
    }

    private ClinicaEntity fetchClinicaById(Long id) {
        return clinicaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Clinica not found with id: " + id));
    }

    private ProfissionalEntity fetchProfissionalById(Long id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional not found with id: " + id));
    }

}
