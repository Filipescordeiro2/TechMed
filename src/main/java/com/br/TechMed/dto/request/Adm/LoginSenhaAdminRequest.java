package com.br.TechMed.dto.request.Adm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSenhaAdminRequest {

    private String login;
    private String senha;
}
