package com.br.TechMed.service.imp.clinica;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.dto.Clinica.ProfissionaisClinicaDTO;
import com.br.TechMed.dto.Clinica.ProfissionaisClinicaDetalhadoDTO;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.clinica.ProfissionaisClinicaEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.clinica.ProfissionaisClinicaRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import com.br.TechMed.service.servicos.clinica.ProfissionaisClinicaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da interface ProfissionaisClinicaService.
 * Fornece métodos para gerenciar a associação entre profissionais e clínicas.
 */
@Service
public class ProfissionaisClinicaServiceImp implements ProfissionaisClinicaService {

    @Autowired
    private ProfissionaisClinicaRepository profissionaisClinicaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Override
    public ProfissionaisClinicaDTO save(ProfissionaisClinicaDTO profissionaisClinicaDTO) {
        if (profissionaisClinicaRepository.existsByClinicaEntityIdAndProfissionalId(
                profissionaisClinicaDTO.getClinicaId(), profissionaisClinicaDTO.getProfissionalId())) {
            throw new RegraDeNegocioException("Já existe um profissional com este ID para a clínica especificada.");
        }

        if (!isClinicaAtiva(profissionaisClinicaDTO.getClinicaId())) {
            throw new RegraDeNegocioException("A clínica especificada não está ativa.");
        }

        if (!isProfissionalAtivo(profissionaisClinicaDTO.getProfissionalId())) {
            throw new RegraDeNegocioException("O profissional especificado não está ativo.");
        }

        ProfissionaisClinicaEntity profissionaisClinica = fromDto(profissionaisClinicaDTO);
        profissionaisClinica = profissionaisClinicaRepository.save(profissionaisClinica);
        return toDto(profissionaisClinica);
    }

    private boolean isClinicaAtiva(Long clinicaId) {
        ClinicaEntity clinica = fetchClinicaById(clinicaId);
        return clinica.getStatusClinica() == StatusUsuario.ATIVO;
    }

    private boolean isProfissionalAtivo(Long profissionalId) {
        ProfissionalEntity profissional = fetchProfissionalById(profissionalId);
        return profissional.getStatusProfissional() == StatusUsuario.ATIVO;
    }

    @Override
    public List<ProfissionaisClinicaDTO> findByProfissionalId(Long profissionalId) {
        return profissionaisClinicaRepository.findByProfissionalId(profissionalId)
                .stream()
                .filter(profissionaisClinica -> profissionaisClinica.getProfissional().getStatusProfissional() == StatusUsuario.ATIVO)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfissionaisClinicaDTO> findByClinicaId(Long clinicaId) {
        return profissionaisClinicaRepository.findByClinicaEntityId(clinicaId)
                .stream()
                .filter(profissionaisClinica -> profissionaisClinica.getClinicaEntity().getStatusClinica() == StatusUsuario.ATIVO)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ProfissionaisClinicaDetalhadoDTO> findByProfissionalIdDetalhada(Long profissionalId) {
        if (!profissionalRepository.existsById(profissionalId)) {
            throw new RegraDeNegocioException("Profissional não encontrado");
        }

        List<ProfissionaisClinicaEntity> profissionaisClinicaEntities = profissionaisClinicaRepository.findByProfissionalId(profissionalId);

        return profissionaisClinicaEntities.stream()
                .filter(profissionaisClinica -> profissionaisClinica.getProfissional().getStatusProfissional() == StatusUsuario.ATIVO)
                .map(profissionaisClinica -> {
                    ProfissionaisClinicaDetalhadoDTO dto = new ProfissionaisClinicaDetalhadoDTO();
                    dto.setId(profissionaisClinica.getId());
                    dto.setClinicaId(profissionaisClinica.getClinicaEntity().getId());
                    dto.setNomeclinica(profissionaisClinica.getClinicaEntity().getNomeClinica());
                    dto.setProfissionalId(profissionaisClinica.getProfissional().getId());
                    dto.setNomeProfissional(profissionaisClinica.getProfissional().getNome());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ProfissionaisClinicaDetalhadoDTO> findByClinicaIdDetalhada(Long clinicaId) {
        if (!clinicaRepository.existsById(clinicaId)) {
            throw new RegraDeNegocioException("Clinica não encontrado");
        }

        List<ProfissionaisClinicaEntity> profissionaisClinicaEntities = profissionaisClinicaRepository.findByClinicaEntityId(clinicaId);

        return profissionaisClinicaEntities.stream()
                .filter(profissionaisClinica -> profissionaisClinica.getClinicaEntity().getStatusClinica() == StatusUsuario.ATIVO)
                .map(profissionaisClinica -> {
                    ProfissionaisClinicaDetalhadoDTO dto = new ProfissionaisClinicaDetalhadoDTO();
                    dto.setId(profissionaisClinica.getId());
                    dto.setClinicaId(profissionaisClinica.getClinicaEntity().getId());
                    dto.setNomeclinica(profissionaisClinica.getClinicaEntity().getNomeClinica());
                    dto.setProfissionalId(profissionaisClinica.getProfissional().getId());
                    dto.setNomeProfissional(profissionaisClinica.getProfissional().getNome());
                    return dto;
                }).collect(Collectors.toList());
    }

    private ProfissionaisClinicaDTO toDto(ProfissionaisClinicaEntity profissionaisClinica) {
        return new ProfissionaisClinicaDTO(
                profissionaisClinica.getId(),
                profissionaisClinica.getClinicaEntity().getId(),
                profissionaisClinica.getProfissional().getId()
        );
    }

    private ProfissionaisClinicaEntity fromDto(ProfissionaisClinicaDTO profissionaisClinicaDTO) {
        ProfissionaisClinicaEntity profissionaisClinica = new ProfissionaisClinicaEntity();
        profissionaisClinica.setId(profissionaisClinicaDTO.getId());
        profissionaisClinica.setClinicaEntity(fetchClinicaById(profissionaisClinicaDTO.getClinicaId()));
        profissionaisClinica.setProfissional(fetchProfissionalById(profissionaisClinicaDTO.getProfissionalId()));
        return profissionaisClinica;
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