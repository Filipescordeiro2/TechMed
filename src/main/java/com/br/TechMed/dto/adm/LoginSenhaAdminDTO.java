package com.br.TechMed.dto.adm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSenhaAdminDTO {

    private String login;
    private String senha;
}
