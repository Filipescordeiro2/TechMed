package com.br.TechMed.dto.response.adm;

import com.br.TechMed.Enum.TipoUsuario;
import lombok.Builder;

@Builder
public record AdminResponse(
        Long id,
        String nome,
        String sobrenome,
        String email,
        String cpf,
        String celular,
        TipoUsuario tipoUsuario
) {}
