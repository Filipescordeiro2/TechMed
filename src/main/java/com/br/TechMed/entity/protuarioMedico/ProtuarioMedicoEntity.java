package com.br.TechMed.entity.protuarioMedico;

import com.br.TechMed.dto.request.prontuarioMedico.ProtuarioMedicoRequest;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prontuario_medico")
public class ProtuarioMedicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProfissionalEntity profissional;

    @ManyToOne
    private ClienteEntity cliente;

    @ManyToOne
    private ClinicaEntity clinica;

    @Column(name = "descricao", length = 10000)
    private String descricao;

    @Column(name = "data_consulta")
    private LocalDate dataConsulta;

    @OneToMany(mappedBy = "protuarioMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamesClienteEntity> exames;

    @OneToMany(mappedBy = "protuarioMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProcedimentosClienteEntity> procedimentos;

    @OneToMany(mappedBy = "protuarioMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicamentosClienteEntity> medicamentos;

    @Column(name = "observacoes")
    private List<String> observacoes;

    public ProtuarioMedicoEntity(ProtuarioMedicoRequest request){
        this.descricao = request.getDescricao();
        this.dataConsulta = request.getDataConsulta();
        this.cliente = new ClienteEntity();
        this.cliente.setId(request.getClienteId());
        this.clinica = new ClinicaEntity();
        this.clinica.setId(request.getClinicaId());
        this.profissional = new ProfissionalEntity();
        this.profissional.setId(request.getProfissionalId());
    }
}