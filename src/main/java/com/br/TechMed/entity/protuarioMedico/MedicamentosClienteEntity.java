package com.br.TechMed.entity.protuarioMedico;

import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medicamentos_cliente")
public class MedicamentosClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "protuario_medico_id")
    private ProtuarioMedicoEntity protuarioMedico;

    @Column(name = "medicamento", length = 255)
    private String medicamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private ProfissionalEntity profissional;
}