package com.br.TechMed.utils.validation;

import com.br.TechMed.dto.request.prontuarioMedico.ProtuarioMedicoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProntuarioMedicoValidation {

    public void validateRequest(ProtuarioMedicoRequest request) {
        if (request.getCpf() == null || request.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF do cliente é obrigatório");
        }
        if (request.getClienteId() == null) {
            throw new IllegalArgumentException("ID do cliente é obrigatório");
        }
        if (request.getClinicaId() == null) {
            throw new IllegalArgumentException("ID da clínica é obrigatório");
        }
        if (request.getProfissionalId() == null) {
            throw new IllegalArgumentException("ID do profissional é obrigatório");
        }
    }

    public void validateCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF do cliente é obrigatório");
        }
    }
}
