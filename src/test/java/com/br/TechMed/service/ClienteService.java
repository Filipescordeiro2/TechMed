package com.br.TechMed.service;

import com.br.TechMed.dto.cliente.ClienteDTO;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {
    ClienteDTO cadastrarCliente(ClienteDTO clienteDTO);

}
