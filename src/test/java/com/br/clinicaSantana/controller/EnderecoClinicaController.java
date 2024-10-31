package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.EnderecoClinicaDTO;
import com.br.clinicaSantana.service.EnderecoClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecoClinicas")
public class EnderecoClinicaController {

    @Autowired
    private EnderecoClinicaService enderecoClinicaService;

    @PostMapping
    public EnderecoClinicaDTO salvarEndereco(@RequestBody EnderecoClinicaDTO enderecoClinicaDTO) {
        return enderecoClinicaService.salvarEndereco(enderecoClinicaDTO);
    }
}
