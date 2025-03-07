package com.br.TechMed.utils.utilitaria;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.dto.response.Clinica.ProfissionaisClinicaDetalhadoResponse;
import com.br.TechMed.dto.response.Clinica.ProfissionaisClinicaResponse;
import com.br.TechMed.entity.clinica.ProfissionaisClinicaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfissionaisClinicaUtils {

    public ProfissionaisClinicaResponse convetProfissionaisClinicaResponse(ProfissionaisClinicaEntity entity){
        return ProfissionaisClinicaResponse.builder()
                .id(entity.getId())
                .clinicaId(entity.getClinicaEntity().getId())
                .profissionalId(entity.getProfissional().getId())
                .build();
    }

    public static List<ProfissionaisClinicaDetalhadoResponse> convertProfisisonaisClinicasDetalalhadoResponse(List<ProfissionaisClinicaEntity> profissionaisClinicaEntities) {
        return profissionaisClinicaEntities.stream()
                .filter(profissionaisClinica -> profissionaisClinica.getProfissional().getStatusProfissional() == StatusUsuario.ATIVO)
                .map(profissionaisClinica -> ProfissionaisClinicaDetalhadoResponse.builder()
                        .id(profissionaisClinica.getId())
                        .clinicaId(profissionaisClinica.getClinicaEntity().getId())
                        .nomeclinica(profissionaisClinica.getClinicaEntity().getNomeClinica())
                        .profissionalId(profissionaisClinica.getProfissional().getId())
                        .nomeProfissional(profissionaisClinica.getProfissional().getNome())
                        .build())
                .collect(Collectors.toList());
    }
}
