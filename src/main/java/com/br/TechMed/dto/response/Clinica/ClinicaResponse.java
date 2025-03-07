package com.br.TechMed.dto.response.Clinica;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.Enum.TipoUsuario;

import lombok.Builder;

import java.util.List;

@Builder
public record ClinicaResponse(
    long id,
    String nomeClinica,
    String descricaoClinica,
    String telefone,
    String celular,
    StatusUsuario statusClinica,
    TipoUsuario tipoUsuario,
    String email,
    String cnpj,
    Long adminId,
    EnderecoClinicaResponse enderecoClinica,
    List<EspecialidadeClinicaResponse> especialidadeClinica,
    List<ProfissionaisClinicaResponse> profissionaisClinica
) {
}