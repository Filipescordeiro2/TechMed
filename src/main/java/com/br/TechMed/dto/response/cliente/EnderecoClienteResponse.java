package com.br.TechMed.dto.response.cliente;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EnderecoClienteResponse(

        Long id,

        @NotNull(message = "CEP é obrigatório")
        String cep,

        @NotNull(message = "Logradouro é obrigatório")
        String logradouro,

        @NotNull(message = "Número é obrigatório")
        String numero,

        String complemento,

        @NotNull(message = "Bairro é obrigatório")
        String bairro,

        @NotNull(message = "Cidade é obrigatória")
        String cidade,

        @NotNull(message = "Estado é obrigatório")
        String estado,

        @NotNull(message = "País é obrigatório")
        String pais
) {}