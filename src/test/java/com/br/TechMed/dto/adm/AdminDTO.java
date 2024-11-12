package com.br.TechMed.dto.adm;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class AdminDTO {

    private Long id;

    @NotNull(message = "Login é obrigatório")
    private String login;

    @NotNull(message = "Senha é obrigatória")
    private String senha;

    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Sobrenome é obrigatório")
    private String sobrenome;

    @Email
    @NotNull(message = "Email é obrigatório")
    private String email;

    @CPF
    @NotNull(message = "CPF é obrigatório")
    private String cpf;

    private String celular;

}
