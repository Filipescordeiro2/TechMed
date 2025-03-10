package com.br.TechMed.dto.response.Agendamento;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AgendamentoReponse(String mesage,
                                 LocalDateTime createdAt) {
}
