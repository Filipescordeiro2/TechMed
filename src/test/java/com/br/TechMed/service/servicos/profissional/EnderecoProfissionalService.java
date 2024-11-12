package com.br.TechMed.service.servicos.profissional;

import com.br.TechMed.dto.profissional.EnderecoProfissionalDTO;
import org.springframework.stereotype.Service;

@Service
public interface EnderecoProfissionalService {

    EnderecoProfissionalDTO salvarEndereco(EnderecoProfissionalDTO enderecoProfissionalDTO);

}
