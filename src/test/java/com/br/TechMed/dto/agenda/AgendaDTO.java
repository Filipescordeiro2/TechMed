package com.br.TechMed.dto.agenda;

import com.br.TechMed.Enum.Jornada;
import com.br.TechMed.Enum.StatusAgenda;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaDTO {

    private Long id;
    @NotNull(message = "O ID do profissional é obrigatório")
    private Long profissionalId;

    @NotNull(message = "O ID da clínica é obrigatório")
    private Long clinicaId;

    @NotNull(message = "O ID da especialidade é obrigatório")
    private Long especialidadeId;

    private LocalTime hora;
    private LocalDate data;
    private Jornada jornada; // Mudou para o tipo correto
    private StatusAgenda statusAgenda; // Mudou para o tipo correto

}
