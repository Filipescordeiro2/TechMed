package com.br.TechMed.service.servicos.clinica;

import com.br.TechMed.dto.Clinica.EspecialidadeClinicaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EspecialidadeClinicaService {

    EspecialidadeClinicaDTO salvarEspecialidade(EspecialidadeClinicaDTO especialidadeClinicaDTO);
    List<EspecialidadeClinicaDTO> buscarEspecialidadePorClinicaId(Long clinicaId);

}
