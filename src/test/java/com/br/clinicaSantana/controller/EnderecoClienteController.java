package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.EnderecoClienteDTO;
import com.br.clinicaSantana.service.EnderecoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
public class EnderecoClienteController {

    @Autowired
    private EnderecoClienteService enderecoClienteService;

    @PostMapping
    public EnderecoClienteDTO salvarEndereco(@RequestBody EnderecoClienteDTO enderecoClienteDTO) {
        return enderecoClienteService.salvarEndereco(enderecoClienteDTO);
    }
}