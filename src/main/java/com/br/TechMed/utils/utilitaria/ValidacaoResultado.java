package com.br.TechMed.utils.utilitaria;

import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidacaoResultado {

    private final ProfissionalEntity profissional;
    private final ClinicaEntity clinica;
    private final EspecialidadeProfissionalEntity especialidade;
}
