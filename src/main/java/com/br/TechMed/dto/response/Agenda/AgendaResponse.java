package com.br.TechMed.dto.response.Agenda;

import com.br.TechMed.Enum.Jornada;
import com.br.TechMed.Enum.StatusAgenda;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AgendaResponse(String message,
                             LocalTime hora,
                             LocalDate data,
                             Jornada jornada,
                             StatusAgenda statusAgenda) {
}
