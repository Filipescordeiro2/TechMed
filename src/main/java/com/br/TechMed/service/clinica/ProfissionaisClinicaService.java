package com.br.TechMed.service.clinica;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.dto.request.Clinica.ProfissionaisClinicaRequest;
import com.br.TechMed.dto.response.Clinica.ProfissionaisClinicaDetalhadoResponse;
import com.br.TechMed.dto.response.Clinica.ProfissionaisClinicaResponse;
import com.br.TechMed.entity.clinica.ProfissionaisClinicaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.clinica.ProfissionaisClinicaRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import com.br.TechMed.utils.utilitaria.ProfissionaisClinicaUtils;
import com.br.TechMed.utils.validation.ProfissionaisClinicaValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfissionaisClinicaService {

    private final ProfissionaisClinicaRepository profissionaisClinicaRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ClinicaRepository clinicaRepository;
    private final ProfissionaisClinicaUtils profissionaisClinicaUtils;
    private final ProfissionaisClinicaValidation profissionaisClinicaValidation;


    public ProfissionaisClinicaResponse save(ProfissionaisClinicaRequest request) {
        profissionaisClinicaValidation.validateProfissionaisClinicaRequest(request);
        var profissionaisClinica = new ProfissionaisClinicaEntity(request);
        var profissionaisClinicaSaved = profissionaisClinicaRepository.save(profissionaisClinica);
        return profissionaisClinicaUtils.convetProfissionaisClinicaResponse(profissionaisClinicaSaved);
    }

    public List<ProfissionaisClinicaResponse> findByProfissionalId(Long profissionalId) {
        return profissionaisClinicaRepository.findByProfissionalId(profissionalId)
                .stream()
                .filter(profissionaisClinica -> profissionaisClinica.getProfissional().getStatusProfissional() == StatusUsuario.ATIVO)
                .map(profissionaisClinicaUtils::convetProfissionaisClinicaResponse)
                .collect(Collectors.toList());
    }

    public List<ProfissionaisClinicaResponse> findByClinicaId(Long clinicaId) {
        return profissionaisClinicaRepository.findByClinicaEntityId(clinicaId)
                .stream()
                .filter(profissionaisClinica -> profissionaisClinica.getClinicaEntity().getStatusClinica() == StatusUsuario.ATIVO)
                .map(profissionaisClinicaUtils::convetProfissionaisClinicaResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProfissionaisClinicaDetalhadoResponse> findByProfissionalIdDetalhada(Long profissionalId) {
        if (!profissionalRepository.existsById(profissionalId)) {
            throw new RegraDeNegocioException("Profissional não encontrado");
        }

        List<ProfissionaisClinicaEntity> profissionaisClinicaEntities = profissionaisClinicaRepository.findByProfissionalId(profissionalId);
        return ProfissionaisClinicaUtils.convertProfisisonaisClinicasDetalalhadoResponse(profissionaisClinicaEntities);
    }

    @Transactional
    public List<ProfissionaisClinicaDetalhadoResponse> findByClinicaIdDetalhada(Long clinicaId) {
        if (!clinicaRepository.existsById(clinicaId)) {
            throw new RegraDeNegocioException("Clinica não encontrado");
        }

        List<ProfissionaisClinicaEntity> profissionaisClinicaEntities = profissionaisClinicaRepository.findByClinicaEntityId(clinicaId);
        return ProfissionaisClinicaUtils.convertProfisisonaisClinicasDetalalhadoResponse(profissionaisClinicaEntities);
    }

}