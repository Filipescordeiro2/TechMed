package com.br.TechMed.service.imp.clinica;

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

    /**
     * Salva a associação entre um profissional e uma clínica no sistema.
     *
     * @param profissionaisClinicaDTO os dados da associação a ser salva
     * @return os dados da associação salva
     */
    @Override
    public ProfissionaisClinicaDTO save(ProfissionaisClinicaDTO profissionaisClinicaDTO) {
        // Verifica se já existe uma combinação de clinica_id e profissional_id
        if (profissionaisClinicaRepository.existsByClinicaEntityIdAndProfissionalId(
                profissionaisClinicaDTO.getClinicaId(), profissionaisClinicaDTO.getProfissionalId())) {
            throw new RegraDeNegocioException("Já existe um profissional com este ID para a clínica especificada.");
        }

        ProfissionaisClinicaEntity profissionaisClinica = fromDto(profissionaisClinicaDTO);
        profissionaisClinica = profissionaisClinicaRepository.save(profissionaisClinica);
        return toDto(profissionaisClinica);
    }

    /**
     * Busca todas as associações de um profissional pelo seu ID.
     *
     * @param profissionalId o ID do profissional
     * @return uma lista de associações do profissional
     */
    @Override
    public List<ProfissionaisClinicaDTO> findByProfissionalId(Long profissionalId) {
        return profissionaisClinicaRepository.findByProfissionalId(profissionalId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Busca todas as associações de uma clínica pelo seu ID.
     *
     * @param clinicaId o ID da clínica
     * @return uma lista de associações da clínica
     */
    @Override
    public List<ProfissionaisClinicaDTO> findByClinicaId(Long clinicaId) {
        return profissionaisClinicaRepository.findByClinicaEntityId(clinicaId)
                .stream()
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

    /**
     * Converte uma entidade ProfissionaisClinicaEntity para um DTO ProfissionaisClinicaDTO.
     *
     * @param profissionaisClinica a entidade da associação
     * @return o DTO da associação
     */
    private ProfissionaisClinicaDTO toDto(ProfissionaisClinicaEntity profissionaisClinica) {
        return new ProfissionaisClinicaDTO(
                profissionaisClinica.getId(),
                profissionaisClinica.getClinicaEntity().getId(),
                profissionaisClinica.getProfissional().getId()
        );
    }

    /**
     * Converte um DTO ProfissionaisClinicaDTO para uma entidade ProfissionaisClinicaEntity.
     *
     * @param profissionaisClinicaDTO o DTO da associação
     * @return a entidade da associação
     */
    private ProfissionaisClinicaEntity fromDto(ProfissionaisClinicaDTO profissionaisClinicaDTO) {
        ProfissionaisClinicaEntity profissionaisClinica = new ProfissionaisClinicaEntity();
        profissionaisClinica.setId(profissionaisClinicaDTO.getId());
        profissionaisClinica.setClinicaEntity(fetchClinicaById(profissionaisClinicaDTO.getClinicaId()));
        profissionaisClinica.setProfissional(fetchProfissionalById(profissionaisClinicaDTO.getProfissionalId()));
        return profissionaisClinica;
    }

    /**
     * Busca uma clínica pelo seu ID.
     *
     * @param id o ID da clínica
     * @return a entidade da clínica
     */
    private ClinicaEntity fetchClinicaById(Long id) {
        return clinicaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Clinica not found with id: " + id));
    }

    /**
     * Busca um profissional pelo seu ID.
     *
     * @param id o ID do profissional
     * @return a entidade do profissional
     */
    private ProfissionalEntity fetchProfissionalById(Long id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Profissional not found with id: " + id));
    }
}