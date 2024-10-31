package com.br.clinicaSantana.service;

import com.br.clinicaSantana.dto.EnderecoProfissionalDTO;
import org.springframework.stereotype.Service;

@Service
public interface EnderecoProfissionalService {

    EnderecoProfissionalDTO salvarEndereco(EnderecoProfissionalDTO enderecoProfissionalDTO);

}
