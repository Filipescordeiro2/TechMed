package com.br.clinicaSantana.service;

import com.br.clinicaSantana.dto.ClienteDTO;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {
    ClienteDTO cadastrarCliente(ClienteDTO clienteDTO);

}
