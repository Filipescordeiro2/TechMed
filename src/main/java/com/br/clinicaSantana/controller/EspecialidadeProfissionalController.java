package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.EspecialidadeProfissionalDTO;
import com.br.clinicaSantana.service.EspecialidadeProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável por gerenciar as operações relacionadas às especialidades dos profissionais no sistema.
 */
@RestController
@RequestMapping("/especialidadeProfissional")
public class EspecialidadeProfissionalController {

    @Autowired
    private EspecialidadeProfissionalService especialidadeProfissionalService;

    /**
     * Salva uma nova especialidade de profissional.
     *
     * @param especialidadeProfissionalDTO Dados da especialidade a ser salva.
     * @return Dados da especialidade salva.
     */
    @PostMapping
    public EspecialidadeProfissionalDTO salvarEspecialidade(@RequestBody EspecialidadeProfissionalDTO especialidadeProfissionalDTO) {
        return especialidadeProfissionalService.salvarEspecialidade(especialidadeProfissionalDTO);
    }
}