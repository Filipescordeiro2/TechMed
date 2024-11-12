package com.br.TechMed.service.servicos.cliente;

import com.br.TechMed.dto.cliente.ClienteDTO;
import com.br.TechMed.dto.cliente.LoginSenhaClienteDTO;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {
    ClienteDTO cadastrarCliente(ClienteDTO clienteDTO);
    ClienteDTO buscarClientePorCpf(String cpf);
    ClienteDTO autenticarCliente(LoginSenhaClienteDTO loginSenhaClienteDTO);
    long contarClientes();

}
