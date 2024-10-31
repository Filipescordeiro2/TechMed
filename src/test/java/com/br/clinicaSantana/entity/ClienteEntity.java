package com.br.clinicaSantana.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_cliente", unique = true)
    @NotNull(message = "Login é obrigatório")
    private String login;

    @Column(name = "senha_cliente")
    @NotNull(message = "Senha é obrigatória")
    private String senha;

    @Column(name = "nome_cliente")
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Column(name = "sobrenome_cliente")
    @NotNull(message = "Sobrenome é obrigatório")
    private String sobrenome;

    @Email
    @Column(name = "email_cliente", unique = true)
    @NotNull(message = "Email é obrigatório")
    private String email;

    @CPF
    @Column(name = "cpf_cliente", unique = true)
    @NotNull(message = "CPF é obrigatório")
    private String cpf;

    @Column(name = "celular_cliente", unique = true)
    private String celular;

    @OneToMany(mappedBy = "clienteEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnderecoClienteEntity> enderecos = new ArrayList<>();
}