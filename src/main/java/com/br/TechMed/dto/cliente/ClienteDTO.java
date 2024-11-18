package com.br.TechMed.dto.cliente;

import com.br.TechMed.dto.Clinica.EnderecoClienteDTO;
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
public class ClienteDTO {

    private Long id;

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

    private EnderecoClienteDTO enderecoCliente;

    private LocalDate dataNascimento; // Adicione este campo
    private Integer idade;


}