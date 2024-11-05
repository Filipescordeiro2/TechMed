package com.br.TechMed.dto;

import com.br.TechMed.dto.Clinica.EnderecoClinicaDTO;
import com.br.TechMed.dto.Clinica.EspecialidadeClinicaDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicaDTO {

    private Long id;

    @NotNull(message = "Nome é obrigatório")
    private String nomeClinica;

    @NotNull(message = "Descrição é obrigatória")
    private String descricaoClinica;

    private String telefone;

    @NotNull(message = "Celular é obrigatório")
    private String celular;

    @Email
    @NotNull(message = "Email é obrigatório")
    private String email;

    @CNPJ
    @NotNull(message = "CNPJ é obrigatório")
    private String cnpj;

    @NotNull(message = "Endereço é obrigatório")
    private EnderecoClinicaDTO enderecoClinica;

    @NotNull(message = "Especialidades são obrigatórias")
    private List<EspecialidadeClinicaDTO> especialidadeClinica;

}
