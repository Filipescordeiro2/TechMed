package com.br.TechMed.service.servicos.adm;

import com.br.TechMed.dto.adm.AdminDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdmService {

    AdminDTO cadastrarAdmin(AdminDTO AdminDTO);

}
