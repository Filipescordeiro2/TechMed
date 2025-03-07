package com.br.TechMed.dto.request.Clinica;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionaisClinicaRequest {

    private Long clinicaId;
    private Long profissionalId; // Add this field
}
