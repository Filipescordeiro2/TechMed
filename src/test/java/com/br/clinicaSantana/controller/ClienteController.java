package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.ClienteDTO;
import com.br.clinicaSantana.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ClienteDTO cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        return clienteService.cadastrarCliente(clienteDTO);
    }
}