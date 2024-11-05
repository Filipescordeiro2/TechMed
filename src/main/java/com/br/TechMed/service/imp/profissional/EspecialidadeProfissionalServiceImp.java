package com.br.TechMed.service.imp.profissional;

import com.br.TechMed.dto.profissional.EspecialidadeProfissionalDTO;
import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.profissional.EspecialidadeProfissionalRepository;
import com.br.TechMed.service.servicos.profissional.EspecialidadeProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    // EspecialidadeProfissionalServiceImp.java
    @Override
    public List<EspecialidadeProfissionalDTO> buscarEspecialidadePorProfissionalId(Long profissionalId) {
        return especialidadeProfissionalRepository.findByProfissionalEntityId(profissionalId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    // Métodos auxiliares de conversão
    private EspecialidadeProfissionalDTO toDto(EspecialidadeProfissionalEntity especialidadeProfissionalEntity) {
        EspecialidadeProfissionalDTO especialidadeProfissionalDTO = new EspecialidadeProfissionalDTO();
        especialidadeProfissionalDTO.setId(especialidadeProfissionalEntity.getId());
        especialidadeProfissionalDTO.setEspecialidades(especialidadeProfissionalEntity.getEspecialidades());
        return especialidadeProfissionalDTO;
    }

    private EspecialidadeProfissionalEntity fromDto(EspecialidadeProfissionalDTO especialidadeProfissionalDTO) {
        EspecialidadeProfissionalEntity especialidadeProfissionalEntity = new EspecialidadeProfissionalEntity();
        especialidadeProfissionalEntity.setEspecialidades(especialidadeProfissionalDTO.getEspecialidades());
        return especialidadeProfissionalEntity;
    }
}