package com.br.TechMed.dto.response.Profissional;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.Enum.TipoUsuario;
import com.br.TechMed.dto.response.Clinica.ProfissionaisClinicaResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ProfissionalResponse(    Long id,
                                       String nome,
                                       String sobrenome,
                                       String email,
                                       String cpf,
                                       String celular,
                                       StatusUsuario StatusProfissional,
                                       TipoUsuario tipoUsuario,
                                       String orgaoRegulador,
                                       String numeroRegistro,
                                       String ufOrgaoRegulador,
                                       List<EspecialidadeProfissionalResponse> especialidadesProfisisonal,
                                       List<ProfissionaisClinicaResponse> profissionaisClinica,
                                       EnderecoProfissionalResponse enderecoProfissional) {
}
