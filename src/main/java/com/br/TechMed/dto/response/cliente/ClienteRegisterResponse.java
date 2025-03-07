package com.br.TechMed.dto.response.cliente;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClienteRegisterResponse(String message,
                                      LocalDateTime createdDate) {
}
