package com.br.clinicaSantana.service;

import com.br.clinicaSantana.dto.EspecialidadeProfissionalDTO;
import org.springframework.stereotype.Service;

@Service
public interface EspecialidadeProfissionalService {

    EspecialidadeProfissionalDTO salvarEspecialidade(EspecialidadeProfissionalDTO especialidadeProfissionalDTO);


}
