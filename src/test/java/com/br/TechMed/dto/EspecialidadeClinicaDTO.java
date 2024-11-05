package com.br.TechMed.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadeClinicaDTO {

    private Long id;

    @NotNull(message = "O nome da especialidade é obrigatório")
    private String nomeEspecialidade;

    @NotNull(message = "A descrição da especialidade é obrigatória")
    private String descricaoEspecialidade;


}
