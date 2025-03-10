package com.br.TechMed.entity.agendamento;

import com.br.TechMed.dto.request.Agendamento.AgendamentoRequest;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.agenda.AgendaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agendamento")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AgendamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agenda_id", nullable = false)
    private AgendaEntity agenda;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    public AgendamentoEntity(AgendamentoRequest request){
        this.agenda = new AgendaEntity();
        this.agenda.setId(request.getAgendaId());
        this.cliente = new ClienteEntity();
        this.cliente.setId(request.getClienteId());
    }

}