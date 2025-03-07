package com.br.TechMed.dto.request.Cliente;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoClienteRequest {

    @NotNull(message = "CEP é obrigatório")
    private String cep;

    @NotNull(message = "Logradouro é obrigatório")
    private String logradouro;

    @NotNull(message = "Número é obrigatório")
    private String numero;

    private String complemento;

    @NotNull(message = "Bairro é obrigatório")
    private String bairro;

    @NotNull(message = "Cidade é obrigatória")
    private String cidade;

    @NotNull(message = "Estado é obrigatório")
    private String estado;

    @NotNull(message = "País é obrigatório")
    private String pais;
}
