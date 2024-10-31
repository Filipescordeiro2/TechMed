package com.br.clinicaSantana.service;

import com.br.clinicaSantana.dto.ClienteDTO;
import com.br.clinicaSantana.dto.LoginSenhaClienteDTO;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {
    ClienteDTO cadastrarCliente(ClienteDTO clienteDTO);
    ClienteDTO buscarClientePorCpf(String cpf);
    ClienteDTO autenticarCliente(LoginSenhaClienteDTO loginSenhaClienteDTO);

}
