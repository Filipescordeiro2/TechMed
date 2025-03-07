package com.br.TechMed.service.servicos.adm;

import com.br.TechMed.dto.adm.AdminDTO;
import com.br.TechMed.dto.adm.LoginSenhaAdminDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdmService {

    AdminDTO cadastrarAdmin(AdminDTO AdminDTO);
    AdminDTO autenticarAdmin(LoginSenhaAdminDTO loginSenhaAdminDTO);

}
