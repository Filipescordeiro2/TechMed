package com.br.TechMed.dto.response.Profissional;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AgendaDetalhadaResponse(
        Long codigoAgenda,
        LocalDate data,
        LocalTime hora,
        String periodoAgenda,
        String statusAgenda,
        String clinica,
        Long codigoClinica,
        String emailClinica,
        String celularClinica,
        String nomeProfissional,
        String nomeEspecialidadeProfissional,
        String descricaoEspecialidadeProfissional
) {}
