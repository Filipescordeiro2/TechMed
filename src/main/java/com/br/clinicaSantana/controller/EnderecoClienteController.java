package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.EnderecoClienteDTO;
import com.br.clinicaSantana.service.EnderecoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos endereços dos clientes no sistema.
 */
@RestController
@RequestMapping("/enderecos")
public class EnderecoClienteController {

    @Autowired
    private EnderecoClienteService enderecoClienteService;

    /**
     * Salva um novo endereço de cliente.
     *
     * @param enderecoClienteDTO Dados do endereço a ser salvo.
     * @return Dados do endereço salvo.
     */
    @PostMapping
    public EnderecoClienteDTO salvarEndereco(@RequestBody EnderecoClienteDTO enderecoClienteDTO) {
        return enderecoClienteService.salvarEndereco(enderecoClienteDTO);
    }
}