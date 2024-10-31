package com.br.clinicaSantana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfissionaisClinicaDTO {

    private Long id;
    private Long clinicaId;
    private Long profissionalId; // Add this field

}
