package com.br.TechMed.dto.request.Cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {

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

    private EnderecoClienteRequest enderecoCliente;

    private LocalDate dataNascimento;

}
