package com.br.TechMed.entity;

import com.br.TechMed.entity.clinica.ClinicaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endereco_clinica")
public class EnderecoClinicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cep_endereco")
    @NotNull(message = "CEP é obrigatório")
    private String cep;

    @Column(name = "logradouro_endereco")
    @NotNull(message = "Logradouro é obrigatório")
    private String logradouro;

    @Column(name = "numero_endereco")
    @NotNull(message = "Número é obrigatório")
    private String numero;

    @Column(name = "complemento_endereco")
    private String complemento;

    @Column(name = "bairro_endereco")
    @NotNull(message = "Bairro é obrigatório")
    private String bairro;

    @Column(name = "cidade_endereco")
    @NotNull(message = "Cidade é obrigatória")
    private String cidade;

    @Column(name = "estado_endereco")
    @NotNull(message = "Estado é obrigatório")
    private String estado;

    @Column(name = "pais_endereco")
    @NotNull(message = "País é obrigatório")
    private String pais;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private ClinicaEntity clinicaEntity;
}
