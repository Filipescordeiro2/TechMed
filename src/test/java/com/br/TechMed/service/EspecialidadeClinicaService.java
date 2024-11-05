package com.br.TechMed.service;

import com.br.TechMed.dto.Clinica.EspecialidadeClinicaDTO;
import org.springframework.stereotype.Service;

@Service
public interface EspecialidadeClinicaService {

    EspecialidadeClinicaDTO salvarEspecialidade(EspecialidadeClinicaDTO especialidadeClinicaDTO);

}
