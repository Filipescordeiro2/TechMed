package com.br.TechMed.entity.adm;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_admin", unique = true)
    @NotNull(message = "Login é obrigatório")
    private String login;

    @Column(name = "senha_admin")
    @NotNull(message = "Senha é obrigatória")
    private String senha;

    @Column(name = "nome_admin")
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Column(name = "sobrenome_admin")
    @NotNull(message = "Sobrenome é obrigatório")
    private String sobrenome;

    @Email
    @Column(name = "email_admin", unique = true)
    @NotNull(message = "Email é obrigatório")
    private String email;

    @CPF
    @Column(name = "cpf_admin", unique = true)
    @NotNull(message = "CPF é obrigatório")
    private String cpf;

    @Column(name = "celular_admin", unique = true)
    private String celular;
}