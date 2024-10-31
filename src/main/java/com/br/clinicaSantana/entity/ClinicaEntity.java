package com.br.clinicaSantana.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clinica")
public class ClinicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nome é obrigatório")
    @Column(name = "nome_clinica")
    private String nomeClinica;

    @NotNull(message = "Descrição é obrigatória")
    @Column(name = "descricao_clinica")
    private String descricaoClinica;

    @Column(name = "telefone_clinica")
    private String telefone;

    @NotNull(message = "Celular é obrigatório")
    @Column(name = "celular_clinica")
    private String celular;

    @Email
    @NotNull(message = "Email é obrigatório")
    @Column(name = "email_clinica")
    private String email;

    @CNPJ
    @Column(name= "cnpj_clinica")
    @NotNull(message = "CNPJ é obrigatório")
    private String cnpj;

    @OneToMany(mappedBy = "clinicaEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnderecoClinicaEntity> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "clinicaEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EspecialidadeClinicaEntity> especialidades = new ArrayList<>();

    @OneToMany(mappedBy = "clinicaEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfissionaisClinicaEntity> profissionais = new ArrayList<>();
}