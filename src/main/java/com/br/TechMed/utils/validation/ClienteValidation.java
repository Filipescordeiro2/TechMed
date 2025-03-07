package com.br.TechMed.utils.validation;

import com.br.TechMed.dto.request.Cliente.LoginSenhaClienteResquest;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.cliente.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteValidation {

    private final ClienteRepository clienteRepository;

    public ClienteEntity buscarClientePorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado com o CPF: " + cpf));
    }

    public ClienteEntity buscarClientePorId(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado com o ID: " + id));
    }

    public ClienteEntity buscarClientePorLogin(LoginSenhaClienteResquest resquest){
        var clienteEntity = clienteRepository.findByLogin(resquest.getLogin())
                .orElseThrow(() -> new RegraDeNegocioException("Login ou senha inválidos"));
        if (!clienteEntity.getSenha().equals(resquest.getSenha())) {
            throw new RegraDeNegocioException("Login ou senha inválidos");
        }
        return clienteEntity;
    }

}
