package com.br.TechMed.entity.clinica;

import com.br.TechMed.dto.request.Cliente.EnderecoClienteRequest;
import com.br.TechMed.dto.request.Clinica.EnderecoClinicaRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um endereço de clínica no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endereco_clinica")
public class EnderecoClinicaEntity {

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
     * Clínica associada ao endereço.
     */
    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private ClinicaEntity clinicaEntity;

    public EnderecoClinicaEntity(EnderecoClinicaRequest request){
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