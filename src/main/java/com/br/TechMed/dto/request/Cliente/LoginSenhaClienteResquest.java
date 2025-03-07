package com.br.TechMed.dto.request.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginSenhaClienteResquest {

    private String login;
    private String senha;
}
