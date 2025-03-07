package com.br.TechMed.service.adm;

import com.br.TechMed.dto.request.Adm.AdminRequest;
import com.br.TechMed.dto.request.Adm.LoginSenhaAdminRequest;
import com.br.TechMed.dto.response.adm.AdminRegisterResponse;
import com.br.TechMed.dto.response.adm.AdminResponse;
import com.br.TechMed.entity.adm.AdminEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.adm.AdmRepository;
import com.br.TechMed.utils.utilitaria.AdminUtils;
import com.br.TechMed.utils.validation.AdminValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public  class AdmService {

    private final AdmRepository admRepository;
    private final AdminUtils adminUtils;
    private final AdminValidation adminValidation;

    @Transactional
    public AdminRegisterResponse cadastrarAdmin(AdminRequest request) {
        log.info("Inicializando o servico de cadastrar admin request --> {}", request);

        try {
            var adminEntity = new AdminEntity(request);
            var  adminEntitySaved = admRepository.save(adminEntity);
            log.info("Admin cadastrado com sucesso --> {}", adminEntitySaved);
            return adminUtils.convetAdminResgisterResponse("Admin cadastrado com sucesso");
        } catch (Exception e) {
            log.error("Erro ao cadastrar admin: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao cadastrar admin: " + e.getMessage());
        }
    }

    public AdminResponse autenticarAdmin(LoginSenhaAdminRequest request) {
        log.info("Inicializando o servico de autenticar admin request --> {}", request);
        try {
            var adminEntity = adminValidation.buscarAdminPorLogin(request);
            log.info("Admin autenticado com sucesso --> {}", adminEntity);
            return adminUtils.convetAdminResponse(adminEntity);
        }catch (Exception e){
            log.error("Erro ao autenticar admin: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao autenticar admin: " + e.getMessage());
        }
    }
}
