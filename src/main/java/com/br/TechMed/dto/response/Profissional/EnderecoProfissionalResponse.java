package com.br.TechMed.dto.response.Profissional;

import lombok.Builder;

@Builder
public record EnderecoProfissionalResponse(  Long id,
                                             String cep,
                                             String logradouro,
                                             String numero,
                                             String complemento,
                                             String bairro,
                                             String cidade,
                                             String estado,
                                             String pais) {
}
