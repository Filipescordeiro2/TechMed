package com.br.TechMed.utils.utilitaria;

import com.br.TechMed.dto.response.Agendamento.AgendamentoDetalhadaResponse;
import com.br.TechMed.dto.response.Agendamento.AgendamentoReponse;
import com.br.TechMed.entity.agenda.AgendaEntity;
import com.br.TechMed.entity.agendamento.AgendamentoEntity;
import com.br.TechMed.entity.cliente.ClienteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AgendamentoUtils {

    public AgendamentoReponse convertAgendamentoResponse(AgendamentoEntity agendamentoEntity){
        return AgendamentoReponse
                .builder()
                .mesage("Agendamento criado com sucesso")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public  List<AgendamentoDetalhadaResponse> createAgendamentoDetalhadaResponse(AgendaEntity agenda, ClienteEntity cliente) {
        var response = AgendamentoDetalhadaResponse.builder()
                .codigoAgenda(agenda.getId())
                .codigoClinica(agenda.getClinicaEntity().getId())
                .data(agenda.getData())
                .hora(agenda.getHora())
                .periodoAgenda(agenda.getJornada().name())
                .statusAgenda(agenda.getStatusAgenda().name())
                .clinica(agenda.getClinicaEntity().getNomeClinica())
                .emailClinica(agenda.getClinicaEntity().getEmail())
                .celularClinica(agenda.getClinicaEntity().getCelular())
                .nomeProfissional(agenda.getProfissional().getNome())
                .sobrenomeProfissional(agenda.getProfissional().getSobrenome())
                .nomeEspecialidadeProfissional(String.valueOf(agenda.getEspecialidadeProfissionalEntity().getEspecialidades()))
                .descricaoEspecialidadeProfissional(agenda.getEspecialidadeProfissionalEntity().getDescricaoEspecialidade())
                .codigoCliente(cliente.getId())
                .nomeCliente(cliente.getNome())
                .sobrenomeCliente(cliente.getSobrenome())
                .emailCliente(cliente.getEmail())
                .celularCliente(cliente.getCelular())
                .cpfCliente(cliente.getCpf())
                .dataDeNascimentoCliente(cliente.getDataNascimento())
                .build();

        return List.of(response);
    }
}
