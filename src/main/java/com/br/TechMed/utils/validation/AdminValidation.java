package com.br.TechMed.utils.validation;

import com.br.TechMed.dto.request.Adm.LoginSenhaAdminRequest;
import com.br.TechMed.entity.adm.AdminEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.adm.AdmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminValidation {

    private final AdmRepository admRepository;

    public AdminEntity buscarAdminPorLogin(LoginSenhaAdminRequest resquest){
        var adminEntity = admRepository.findByLogin(resquest.getLogin())
                .orElseThrow(() -> new RegraDeNegocioException("Login ou senha inválidos"));
        if (!adminEntity.getSenha().equals(resquest.getSenha())) {
            throw new RegraDeNegocioException("Login ou senha inválidos");
        }
        return adminEntity;
    }
}
