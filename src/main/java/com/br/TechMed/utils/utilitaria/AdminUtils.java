package com.br.TechMed.utils.utilitaria;

import com.br.TechMed.dto.response.adm.AdminRegisterResponse;
import com.br.TechMed.dto.response.adm.AdminResponse;
import com.br.TechMed.entity.adm.AdminEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AdminUtils {

    public AdminResponse convetAdminResponse(AdminEntity entity){
        return AdminResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .sobrenome(entity.getSobrenome())
                .email(entity.getEmail())
                .cpf(entity.getCpf())
                .celular(entity.getCelular())
                .tipoUsuario(entity.getTipoUsuario())
                .build();
    }

    public AdminRegisterResponse convetAdminResgisterResponse(String message){
        return AdminRegisterResponse.builder()
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
