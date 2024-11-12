package com.br.TechMed.dto.profissional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSenhaProfissionalDTO {

    private String login;
    private String senha;
}
