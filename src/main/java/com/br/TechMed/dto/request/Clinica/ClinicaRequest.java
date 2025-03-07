package com.br.TechMed.dto.request.Clinica;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.Enum.TipoUsuario;
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
public class ClinicaRequest {

    @NotNull(message = "Nome é obrigatório")
    private String nomeClinica;

    @NotNull(message = "Descrição é obrigatória")
    private String descricaoClinica;

    private String telefone;

    @NotNull(message = "Celular é obrigatório")
    private String celular;

    private StatusUsuario StatusClinica;

    private TipoUsuario tipoUsuario;

    @Email
    @NotNull(message = "Email é obrigatório")
    private String email;

    @CNPJ
    @NotNull(message = "CNPJ é obrigatório")
    private String cnpj;

    @NotNull(message = "ID do admin é obrigatório")
    private Long adminId;

    @NotNull(message = "Endereço é obrigatório")
    private EnderecoClinicaRequest enderecoClinica;

    @NotNull(message = "Especialidades são obrigatórias")
    private List<EspecialidadeClinicaRequest> especialidadeClinica;

    private List<ProfissionaisClinicaRequest> profissionaisClinica;

}
