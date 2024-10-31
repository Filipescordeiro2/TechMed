package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.dto.ProfissionaisClinicaDTO;
import com.br.clinicaSantana.entity.ClinicaEntity;
import com.br.clinicaSantana.entity.ProfissionaisClinicaEntity;
import com.br.clinicaSantana.entity.ProfissionalEntity;
import com.br.clinicaSantana.exception.RegraDeNegocioException;
import com.br.clinicaSantana.repository.ClinicaRepository;
import com.br.clinicaSantana.repository.ProfissionaisClinicaRepository;
import com.br.clinicaSantana.repository.ProfissionalRepository;
import com.br.clinicaSantana.service.ProfissionaisClinicaService;
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