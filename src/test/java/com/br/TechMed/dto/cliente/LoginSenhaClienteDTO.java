package com.br.TechMed.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginSenhaClienteDTO {

    private String login;
    private String senha;
}
