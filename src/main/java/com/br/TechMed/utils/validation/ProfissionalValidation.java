package com.br.TechMed.utils.validation;

import com.br.TechMed.dto.request.Profissional.LoginSenhaProfissionalRequest;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.adm.AdmRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfissionalValidation {

    private final AdmRepository adminRepository;
    private final ProfissionalRepository profissionalRepository;

    public void validarAdminExistente(Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new RegraDeNegocioException("Admin não encontrado");
        }
    }

    public ProfissionalEntity buscarProfissionalPorLogin(LoginSenhaProfissionalRequest resquest){
        var profissionalEntity = profissionalRepository.findByLogin(resquest.getLogin())
                .orElseThrow(() -> new RegraDeNegocioException("Login ou senha inválidos"));
        if (!profissionalEntity.getSenha().equals(resquest.getSenha())) {
            throw new RegraDeNegocioException("Login ou senha inválidos");
        }
        return profissionalEntity;
    }
}
