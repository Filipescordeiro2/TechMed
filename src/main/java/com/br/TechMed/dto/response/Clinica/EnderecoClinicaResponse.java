package com.br.TechMed.dto.response.Clinica;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EnderecoClinicaResponse(        Long id,
                                              String cep,
                                              String logradouro,
                                              String numero,
                                              String complemento,
                                              String bairro,
                                              String cidade,
                                              String estado,
                                              String pais) {
}