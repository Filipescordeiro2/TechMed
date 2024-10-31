package com.br.clinicaSantana.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um endereço de profissional no sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "endereco_profissional")
public class EnderecoProfissionalEntity {

    /**
     * Identificador único do endereço.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * CEP do endereço.
     */
    @Column(name = "cep_endereco")
    @NotNull(message = "CEP é obrigatório")
    private String cep;

    /**
     * Logradouro do endereço.
     */
    @Column(name = "logradouro_endereco")
    @NotNull(message = "Logradouro é obrigatório")
    private String logradouro;

    /**
     * Número do endereço.
     */
    @Column(name = "numero_endereco")
    @NotNull(message = "Número é obrigatório")
    private String numero;

    /**
     * Complemento do endereço.
     */
    @Column(name = "complemento_endereco")
    private String complemento;

    /**
     * Bairro do endereço.
     */
    @Column(name = "bairro_endereco")
    @NotNull(message = "Bairro é obrigatório")
    private String bairro;

    /**
     * Cidade do endereço.
     */
    @Column(name = "cidade_endereco")
    @NotNull(message = "Cidade é obrigatória")
    private String cidade;

    /**
     * Estado do endereço.
     */
    @Column(name = "estado_endereco")
    @NotNull(message = "Estado é obrigatório")
    private String estado;

    /**
     * País do endereço.
     */
    @Column(name = "pais_endereco")
    @NotNull(message = "País é obrigatório")
    private String pais;

    /**
     * Profissional associado ao endereço.
     */
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalEntity profissionalEntity;

}