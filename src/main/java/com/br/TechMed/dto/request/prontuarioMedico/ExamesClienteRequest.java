package com.br.TechMed.dto.request.prontuarioMedico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamesClienteRequest {
    private Long protuarioMedicoId;
    private String exame;
}
