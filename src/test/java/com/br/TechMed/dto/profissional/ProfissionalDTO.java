package com.br.TechMed.dto.profissional;

import com.br.TechMed.dto.Clinica.ProfissionaisClinicaDTO;
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
public class ProfissionalDTO {

    private Long id;

    @NotNull(message = "Login é obrigatório")
    private String login;

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

    private String celular;

    private List<EspecialidadeProfissionalDTO> especialidades;

    private List<ProfissionaisClinicaDTO> clinicas;

    private List<EnderecoProfissionalDTO> enderecos;
}