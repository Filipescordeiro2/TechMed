package com.br.TechMed.dto.request.Profissional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSenhaProfissionalRequest {
    private String login;
    private String senha;
}
