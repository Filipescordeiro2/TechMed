package com.br.TechMed.entity.profissional;

import com.br.TechMed.entity.clinica.ProfissionaisClinicaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um profissional no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profissional")
public class ProfissionalEntity {

    /**
     * Identificador único do profissional.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Login do profissional.
     */
    @Column(name = "login_profissional", unique = true)
    @NotNull(message = "Login é obrigatório")
    private String login;

    /**
     * Senha do profissional.
     */
    @Column(name = "senha_profissional")
    @NotNull(message = "Senha é obrigatória")
    private String senha;

    /**
     * Nome do profissional.
     */
    @Column(name = "nome_profissional")
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    /**
     * Sobrenome do profissional.
     */
    @Column(name = "sobrenome_profissional")
    @NotNull(message = "Sobrenome é obrigatório")
    private String sobrenome;

    /**
     * Email do profissional.
     */
    @Email
    @Column(name = "email_profissional", unique = true)
    @NotNull(message = "Email é obrigatório")
    private String email;

    /**
     * CPF do profissional.
     */
    @CPF
    @Column(name = "cpf_profissional", unique = true)
    @NotNull(message = "CPF é obrigatório")
    private String cpf;

    /**
     * Celular do profissional.
     */
    @Column(name = "celular_cliente", unique = true)
    private String celular;

    /**
     * Lista de endereços associados ao profissional.
     */
    @OneToMany(mappedBy = "profissionalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnderecoProfissionalEntity> enderecos = new ArrayList<>();

    /**
     * Lista de especialidades associadas ao profissional.
     */
    @OneToMany(mappedBy = "profissionalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EspecialidadeProfissionalEntity> especialidades = new ArrayList<>();

    /**
     * Lista de clínicas associadas ao profissional.
     */
    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfissionaisClinicaEntity> clinicas = new ArrayList<>();

    public ProfissionalEntity(Long id) {
        this.id = id;
    }

}