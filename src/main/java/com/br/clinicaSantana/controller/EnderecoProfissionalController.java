package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.EnderecoProfissionalDTO;
import com.br.clinicaSantana.service.EnderecoProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos endereços dos profissionais no sistema.
 */
@RestController
@RequestMapping("/enderecoProfissionais")
public class EnderecoProfissionalController {

    @Autowired
    private EnderecoProfissionalService enderecoProfissionalService;

    /**
     * Salva um novo endereço de profissional.
     *
     * @param enderecoProfissionalDTO Dados do endereço a ser salvo.
     * @return Dados do endereço salvo.
     */
    @PostMapping
    public EnderecoProfissionalDTO salvarEndereco(@RequestBody EnderecoProfissionalDTO enderecoProfissionalDTO) {
        return enderecoProfissionalService.salvarEndereco(enderecoProfissionalDTO);
    }
}