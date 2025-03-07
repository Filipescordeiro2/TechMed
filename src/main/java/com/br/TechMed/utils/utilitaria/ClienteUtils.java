package com.br.TechMed.utils.utilitaria;

import com.br.TechMed.dto.response.cliente.ClienteRegisterResponse;
import com.br.TechMed.dto.response.cliente.ClienteResponse;
import com.br.TechMed.dto.response.cliente.EnderecoClienteResponse;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.cliente.EnderecoClienteEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClienteUtils {

    public ClienteResponse convertClientResponse(ClienteEntity clienteEntity) {
        log.info("Converting ClienteEntity to ClienteResponse --> {}", clienteEntity);
        ClienteResponse response = ClienteResponse.builder()
                .id(clienteEntity.getId())
                .nome(clienteEntity.getNome())
                .sobrenome(clienteEntity.getSobrenome())
                .email(clienteEntity.getEmail())
                .cpf(clienteEntity.getCpf())
                .celular(clienteEntity.getCelular())
                .dataNascimento(clienteEntity.getDataNascimento())
                .idade(clienteEntity.getIdade())
                .enderecoCliente(convertEnderecoResponse(clienteEntity.getEnderecos().get(0)))
                .build();
        log.info("Converted ClienteResponse --> {}", response);
        return response;
    }

    public EnderecoClienteResponse convertEnderecoResponse(EnderecoClienteEntity enderecoEntity) {
        log.info("Converting EnderecoClienteEntity to EnderecoClienteResponse --> {}", enderecoEntity);
        EnderecoClienteResponse response = EnderecoClienteResponse.builder()
                .id(enderecoEntity.getId())
                .cep(enderecoEntity.getCep())
                .logradouro(enderecoEntity.getLogradouro())
                .numero(enderecoEntity.getNumero())
                .complemento(enderecoEntity.getComplemento())
                .bairro(enderecoEntity.getBairro())
                .cidade(enderecoEntity.getCidade())
                .estado(enderecoEntity.getEstado())
                .pais(enderecoEntity.getPais())
                .build();
        log.info("Converted EnderecoClienteResponse --> {}", response);
        return response;
    }

    public ClienteRegisterResponse convertClientRegisterResponse(String message) {
        log.info("Creating ClienteRegisterResponse with message --> {}", message);
        ClienteRegisterResponse response = ClienteRegisterResponse.builder()
                .message(message)
                .createdDate(LocalDateTime.now())
                .build();
        log.info("Created ClienteRegisterResponse --> {}", response);
        return response;
    }
}