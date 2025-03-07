package com.br.TechMed.entity.clinica;

import com.br.TechMed.dto.request.Clinica.ProfissionaisClinicaRequest;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profissionais_clinica")
public class ProfissionaisClinicaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalEntity profissional;


    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private ClinicaEntity clinicaEntity;

    public ProfissionaisClinicaEntity(ProfissionaisClinicaRequest request){
        this.profissional = new ProfissionalEntity();
        this.profissional.setId(request.getProfissionalId());
        this.clinicaEntity = new ClinicaEntity();
        this.clinicaEntity.setId(request.getClinicaId());
    }
}