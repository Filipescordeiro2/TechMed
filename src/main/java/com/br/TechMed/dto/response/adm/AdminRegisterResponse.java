package com.br.TechMed.dto.response.adm;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AdminRegisterResponse(String message,
                                    LocalDateTime createdAt) {
}
