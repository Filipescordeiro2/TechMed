package com.br.clinicaSantana.service;

import com.br.clinicaSantana.dto.EspecialidadeClinicaDTO;
import org.springframework.stereotype.Service;

@Service
public interface EspecialidadeClinicaService {

    EspecialidadeClinicaDTO salvarEspecialidade(EspecialidadeClinicaDTO especialidadeClinicaDTO);

}
