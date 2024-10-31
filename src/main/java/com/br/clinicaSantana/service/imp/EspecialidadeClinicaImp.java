package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.Enum.Especialidades;
import com.br.clinicaSantana.dto.EspecialidadeClinicaDTO;
import com.br.clinicaSantana.entity.EspecialidadeClinicaEntity;
import com.br.clinicaSantana.exception.RegraDeNegocioException;
import com.br.clinicaSantana.repository.EspecialidadeClinicaRepository;
import com.br.clinicaSantana.service.EspecialidadeClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementação da interface EspecialidadeClinicaService.
 * Fornece métodos para gerenciar especialidades de clínicas.
 */
@Service
public class EspecialidadeClinicaImp implements EspecialidadeClinicaService {

    @Autowired
    private EspecialidadeClinicaRepository especialidadeClinicaRepository;

    /**
     * Salva uma especialidade de clínica no sistema.
     *
     * @param especialidadeClinicaDTO os dados da especialidade da clínica a ser salva
     * @return os dados da especialidade da clínica salva
     */
    @Override
    public EspecialidadeClinicaDTO salvarEspecialidade(EspecialidadeClinicaDTO especialidadeClinicaDTO) {
        try {
            EspecialidadeClinicaEntity especialidadeClinicaEntity = fromDto(especialidadeClinicaDTO);
            especialidadeClinicaEntity = especialidadeClinicaRepository.save(especialidadeClinicaEntity);
            return toDto(especialidadeClinicaEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao salvar a especialidade da clínica");
        }
    }

    /**
     * Converte uma entidade EspecialidadeClinicaEntity para um DTO EspecialidadeClinicaDTO.
     *
     * @param especialidadeClinicaEntity a entidade da especialidade da clínica
     * @return o DTO da especialidade da clínica
     */
    private EspecialidadeClinicaDTO toDto(EspecialidadeClinicaEntity especialidadeClinicaEntity) {
        EspecialidadeClinicaDTO especialidadeClinicaDTO = new EspecialidadeClinicaDTO();
        especialidadeClinicaDTO.setId(especialidadeClinicaEntity.getId());
        especialidadeClinicaDTO.setEspecialidades(especialidadeClinicaEntity.getEspecialidades());
        return especialidadeClinicaDTO;
    }

    /**
     * Converte um DTO EspecialidadeClinicaDTO para uma entidade EspecialidadeClinicaEntity.
     *
     * @param especialidadeClinicaDTO o DTO da especialidade da clínica
     * @return a entidade da especialidade da clínica
     */
    private EspecialidadeClinicaEntity fromDto(EspecialidadeClinicaDTO especialidadeClinicaDTO) {
        EspecialidadeClinicaEntity especialidadeClinicaEntity = new EspecialidadeClinicaEntity();
        especialidadeClinicaEntity.setEspecialidades(especialidadeClinicaDTO.getEspecialidades());
        return especialidadeClinicaEntity;
    }
}