package com.br.TechMed.service.imp.cliente;

import com.br.TechMed.dto.request.cliente.ClienteRequest;
import com.br.TechMed.dto.request.cliente.LoginSenhaClienteResquest;
import com.br.TechMed.dto.response.cliente.ClienteRegisterResponse;
import com.br.TechMed.dto.response.cliente.ClienteResponse;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.cliente.EnderecoClienteEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.cliente.ClienteRepository;
import com.br.TechMed.repository.cliente.EnderecoClienteRepository;
import com.br.TechMed.utils.utilitaria.ClienteUtils;
import com.br.TechMed.utils.validation.ClienteValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoClienteRepository enderecoClienteRepository;
    private final ClienteUtils clienteUtils;
    private final ClienteValidation clienteValidation;

    @Transactional
    public ClienteRegisterResponse cadastrarCliente(ClienteRequest request) {
        log.info("Inicializando o servico de cadastrar cliente request --> {}", request);
        try {
            var cliente = new ClienteEntity(request);
            var endereco = new EnderecoClienteEntity(request.getEnderecoCliente());
            cliente.getEnderecos().add(endereco);
            endereco.setClienteEntity(cliente);
            clienteRepository.save(cliente);
            enderecoClienteRepository.save(endereco);
            log.info("Cliente cadastrado com sucesso --> {}", cliente);
            log.info("Endereco do cliente cadastrado com sucesso --> {}", endereco);
            return clienteUtils.convertClientRegisterResponse("Cliente cadastrado com sucesso");
        } catch (Exception e) {
            log.error("Erro ao cadastrar cliente: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public ClienteResponse buscarClientePorCpf(String cpf) {
        log.info("Inicializando o servico de buscar cliente por CPF --> {}", cpf);
        try {
            var clienteEntity = clienteValidation.buscarClientePorCpf(cpf);
            log.info("Cliente encontrado --> {}", clienteEntity);
            return clienteUtils.convertClientResponse(clienteEntity);
        } catch (Exception e) {
            log.error("Erro ao buscar cliente por CPF: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao buscar cliente por CPF: " + e.getMessage());
        }
    }

    public ClienteResponse autenticarCliente(LoginSenhaClienteResquest resquest) {
        log.info("Inicializando o servico de autenticar cliente request --> {}", resquest);
        try {
            var clienteEntity = clienteRepository.findByLogin(resquest.getLogin())
                    .orElseThrow(() -> new RegraDeNegocioException("Login ou senha inválidos"));
            if (!clienteEntity.getSenha().equals(resquest.getSenha())) {
                throw new RegraDeNegocioException("Login ou senha inválidos");
            }
            log.info("Cliente autenticado com sucesso --> {}", clienteEntity);
            return clienteUtils.convertClientResponse(clienteEntity);
        } catch (Exception e) {
            log.error("Erro ao autenticar cliente: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao autenticar cliente: " + e.getMessage());
        }
    }

    public long contarClientes() {
        log.info("Inicializando o servico de contar clientes");
        try {
            long count = clienteRepository.count();
            log.info("Total de clientes --> {}", count);
            return count;
        } catch (Exception e) {
            log.error("Erro ao contar clientes: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao contar clientes: " + e.getMessage());
        }
    }
}