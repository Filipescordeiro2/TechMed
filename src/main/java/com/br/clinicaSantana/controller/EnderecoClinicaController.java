package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.EnderecoClinicaDTO;
import com.br.clinicaSantana.service.EnderecoClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos endereços das clínicas no sistema.
 */
@RestController
@RequestMapping("/enderecoClinicas")
public class EnderecoClinicaController {

    @Autowired
    private EnderecoClinicaService enderecoClinicaService;

    /**
     * Salva um novo endereço de clínica.
     *
     * @param enderecoClinicaDTO Dados do endereço a ser salvo.
     * @return Dados do endereço salvo.
     */
    @PostMapping
    public EnderecoClinicaDTO salvarEndereco(@RequestBody EnderecoClinicaDTO enderecoClinicaDTO) {
        return enderecoClinicaService.salvarEndereco(enderecoClinicaDTO);
    }
}