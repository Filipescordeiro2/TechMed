package com.br.TechMed.service.servicos.profissional;

import com.br.TechMed.dto.profissional.EspecialidadeProfissionalDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EspecialidadeProfissionalService {

    EspecialidadeProfissionalDTO salvarEspecialidade(EspecialidadeProfissionalDTO especialidadeProfissionalDTO);
    List<EspecialidadeProfissionalDTO> buscarEspecialidadePorProfissionalId(Long profissionalId);

}