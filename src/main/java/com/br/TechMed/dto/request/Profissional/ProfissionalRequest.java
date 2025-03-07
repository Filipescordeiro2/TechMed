package com.br.TechMed.dto.request.Profissional;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.Enum.TipoUsuario;
import com.br.TechMed.dto.response.Clinica.ProfissionaisClinicaResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfissionalRequest {

    @NotNull(message = "Senha é obrigatória")
    private String senha;

    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Sobrenome é obrigatório")
    private String sobrenome;

    @Email
    @NotNull(message = "Email é obrigatório")
    private String email;

    @CPF
    @NotNull(message = "CPF é obrigatório")
    private String cpf;

    @NotNull(message = "ID do admin é obrigatório")
    private Long adminId;

    @NotNull(message = "ID do admin é obrigatório")
    private String orgaoRegulador;

    @NotNull(message = "NumeroRegistro é obrigatório")
    private String numeroRegistro;

    @NotNull(message = "Uf Orgao Regulador é obrigatório")
    private String ufOrgaoRegulador;

    private String celular;

    private StatusUsuario StatusProfissional;

    private TipoUsuario tipoUsuario;

    private EnderecoProfissionalRequest endereco;

    private List<EspecialidadeProfissionalRequest> especialidades;

    private List<ProfissionaisClinicaResponse> clinicas;

}
