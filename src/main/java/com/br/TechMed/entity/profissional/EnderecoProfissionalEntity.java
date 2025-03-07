package com.br.TechMed.entity.profissional;

import com.br.TechMed.dto.request.Profissional.EnderecoProfissionalRequest;
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
    private String cep;

    /**
     * Logradouro do endereço.
     */
    @Column(name = "logradouro_endereco")
    private String logradouro;

    /**
     * Número do endereço.
     */
    @Column(name = "numero_endereco")
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
    private String bairro;

    /**
     * Cidade do endereço.
     */

    private String cidade;

    /**
     * Estado do endereço.
     */
    @Column(name = "estado_endereco")
    private String estado;

    /**
     * País do endereço.
     */
    @Column(name = "pais_endereco")
    private String pais;

    /**
     * Profissional associado ao endereço.
     */
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalEntity profissionalEntity;

    public EnderecoProfissionalEntity(EnderecoProfissionalRequest request){
        this.cep = request.getCep();
        this.logradouro = request.getLogradouro();
        this.numero = request.getNumero();
        this.complemento = request.getComplemento();
        this.bairro = request.getBairro();
        this.cidade = request.getCidade();
        this.estado = request.getEstado();
        this.pais = request.getPais();
    }

}