package com.br.TechMed.entity.cliente;

import com.br.TechMed.Enum.TipoUsuario;
import com.br.TechMed.dto.request.Cliente.ClienteRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um cliente no sistema.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
public class ClienteEntity {

    /**
     * Identificador único do cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Login do cliente.
     */
    @Column(name = "login_cliente", unique = true)
    @NotNull(message = "Login é obrigatório")
    private String login;

    /**
     * Senha do cliente.
     */
    @Column(name = "senha_cliente")
    @NotNull(message = "Senha é obrigatória")
    private String senha;

    /**
     * Nome do cliente.
     */
    @Column(name = "nome_cliente")
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    /**
     * Sobrenome do cliente.
     */
    @Column(name = "sobrenome_cliente")
    @NotNull(message = "Sobrenome é obrigatório")
    private String sobrenome;

    /**
     * Email do cliente.
     */
    @Email
    @Column(name = "email_cliente", unique = true)
    @NotNull(message = "Email é obrigatório")
    private String email;

    /**
     * CPF do cliente.
     */
    @CPF
    @Column(name = "cpf_cliente", unique = true)
    @NotNull(message = "CPF é obrigatório")
    private String cpf;

    /**
     * Celular do cliente.
     */
    @Column(name = "celular_cliente", unique = true)
    private String celular;

    @Column(name = "tipo_usuario")
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Column(name="data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "idade")
    private Integer idade;

    /**
     * Lista de endereços do cliente.
     */
    @OneToMany(mappedBy = "clienteEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<EnderecoClienteEntity> enderecos = new ArrayList<>();

    public ClienteEntity(Long id) {
        this.id = id;
    }

    private Integer calcularIdade() {
        if (this.dataNascimento != null) {
            return Period.between(this.dataNascimento, LocalDate.now()).getYears();
        }
        return null;
    }

    public ClienteEntity (ClienteRequest request){
        this.login = request.getEmail();
        this.nome = request.getNome();
        this.sobrenome = request.getSobrenome();
        this.email = request.getEmail();
        this.cpf = request.getCpf();
        this.celular = request.getCelular();
        this.senha = request.getSenha();
        this.dataNascimento = request.getDataNascimento();
        this.tipoUsuario = TipoUsuario.PACIENTE;
        this.idade = calcularIdade();
    }
}