package com.br.TechMed.dto.response.cliente;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ClienteResponse(
        Long id,
        String nome,
        String sobrenome,
        String email,
        String cpf,
        String celular,
        EnderecoClienteResponse enderecoCliente,
        LocalDate dataNascimento,
        Integer idade
) {}
