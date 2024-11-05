package com.br.TechMed.dto.prontuarioMedico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentosClienteDTO {
    private Long id;
    private Long protuarioMedicoId;
    private String medicamento;
}