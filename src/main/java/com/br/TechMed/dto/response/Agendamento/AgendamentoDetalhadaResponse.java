package com.br.TechMed.dto.response.Agendamento;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AgendamentoDetalhadaResponse(Long codigoAgenda,
                                           LocalDate data,
                                           LocalTime hora,
                                           String periodoAgenda,
                                           String statusAgenda,
                                           String clinica,
                                           Long codigoClinica,
                                           String emailClinica,
                                           String celularClinica,
                                           String nomeProfissional,
                                           String sobrenomeProfissional,
                                           String nomeEspecialidadeProfissional,
                                           String descricaoEspecialidadeProfissional,
                                           Long codigoCliente,
                                           String nomeCliente,
                                           String sobrenomeCliente,
                                           String emailCliente,
                                           String celularCliente,
                                           String cpfCliente,
                                           LocalDate dataDeNascimentoCliente) {
}
