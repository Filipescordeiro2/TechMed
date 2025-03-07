package com.br.TechMed.utils.validation;

import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.adm.AdmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClinicaValidation {

    private final AdmRepository adminRepository;

    public void validarAdminExistente(Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new RegraDeNegocioException("Admin não encontrado");
        }
    }
}