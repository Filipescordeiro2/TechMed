package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.Enum.Especialidades;
import com.br.clinicaSantana.dto.EspecialidadeProfissionalDTO;
import com.br.clinicaSantana.entity.EspecialidadeProfissionalEntity;
import com.br.clinicaSantana.exception.RegraDeNegocioException;
import com.br.clinicaSantana.repository.EspecialidadeProfissionalRepository;
import com.br.clinicaSantana.service.EspecialidadeProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementação da interface EspecialidadeProfissionalService.
 * Fornece métodos para gerenciar especialidades de profissionais.
 */
@Service
public class EspecialidadeProfissionalServiceImp implements EspecialidadeProfissionalService {

    @Autowired
    private EspecialidadeProfissionalRepository especialidadeProfissionalRepository;

    /**
     * Salva uma especialidade de profissional no sistema.
     *
     * @param especialidadeProfissionalDTO os dados da especialidade do profissional a ser salva
     * @return os dados da especialidade do profissional salva
     */
    @Override
    public EspecialidadeProfissionalDTO salvarEspecialidade(EspecialidadeProfissionalDTO especialidadeProfissionalDTO) {
        try {
            EspecialidadeProfissionalEntity especialidadeProfissionalEntity = fromDto(especialidadeProfissionalDTO);
            especialidadeProfissionalEntity = especialidadeProfissionalRepository.save(especialidadeProfissionalEntity);
            return toDto(especialidadeProfissionalEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao salvar a especialidade do profissional");
        }
    }

    /**
     * Converte uma entidade EspecialidadeProfissionalEntity para um DTO EspecialidadeProfissionalDTO.
     *
     * @param especialidadeProfissionalEntity a entidade da especialidade do profissional
     * @return o DTO da especialidade do profissional
     */
    private EspecialidadeProfissionalDTO toDto(EspecialidadeProfissionalEntity especialidadeProfissionalEntity) {
        EspecialidadeProfissionalDTO especialidadeProfissionalDTO = new EspecialidadeProfissionalDTO();
        especialidadeProfissionalDTO.setId(especialidadeProfissionalEntity.getId());
        especialidadeProfissionalDTO.setEspecialidades(especialidadeProfissionalEntity.getEspecialidades());
        return especialidadeProfissionalDTO;
    }

    /**
     * Converte um DTO EspecialidadeProfissionalDTO para uma entidade EspecialidadeProfissionalEntity.
     *
     * @param especialidadeProfissionalDTO o DTO da especialidade do profissional
     * @return a entidade da especialidade do profissional
     */
    private EspecialidadeProfissionalEntity fromDto(EspecialidadeProfissionalDTO especialidadeProfissionalDTO) {
        EspecialidadeProfissionalEntity especialidadeProfissionalEntity = new EspecialidadeProfissionalEntity();
        especialidadeProfissionalEntity.setEspecialidades(especialidadeProfissionalDTO.getEspecialidades());
        return especialidadeProfissionalEntity;
    }
}