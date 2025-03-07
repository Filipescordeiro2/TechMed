package com.br.TechMed.utils.utilitaria;

import com.br.TechMed.dto.request.Clinica.ClinicaRequest;
import com.br.TechMed.dto.response.Clinica.ClinicaResponse;
import com.br.TechMed.dto.response.Clinica.EnderecoClinicaResponse;
import com.br.TechMed.dto.response.Clinica.EspecialidadeClinicaResponse;
import com.br.TechMed.entity.adm.ClinicasAdminEntity;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.clinica.EnderecoClinicaEntity;
import com.br.TechMed.entity.clinica.EspecialidadeClinicaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.service.clinica.ProfissionaisClinicaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClinicaUtils {

    private final HttpServletRequest request;
    private final ProfissionaisClinicaService profissionaisClinicaService;

    public ClinicaResponse convertClinicaResponse(ClinicaEntity clinicaEntity) {
        ClinicaResponse response = ClinicaResponse.builder()
                .id(clinicaEntity.getId())
                .nomeClinica(clinicaEntity.getNomeClinica())
                .descricaoClinica(clinicaEntity.getDescricaoClinica())
                .telefone(clinicaEntity.getTelefone())
                .celular(clinicaEntity.getCelular())
                .email(clinicaEntity.getEmail())
                .cnpj(clinicaEntity.getCnpj())
                .statusClinica(clinicaEntity.getStatusClinica())
                .tipoUsuario(clinicaEntity.getTipoUsuario())
                .enderecoClinica(convertEnderecoResponse(clinicaEntity.getEnderecos().get(0)))
                .especialidadeClinica(clinicaEntity.getEspecialidades().stream()
                        .map(this::convertEspecialidadeResponse)
                        .collect(Collectors.toList()))
                .profissionaisClinica(profissionaisClinicaService.findByClinicaId(clinicaEntity.getId()))
                .build();
        return response;
    }

    public EspecialidadeClinicaResponse convertEspecialidadeResponse(EspecialidadeClinicaEntity especialidadeClinicaEntity) {
        return new EspecialidadeClinicaResponse(
                especialidadeClinicaEntity.getId(),
                especialidadeClinicaEntity.getEspecialidades()
        );
    }

    public EnderecoClinicaResponse convertEnderecoResponse(EnderecoClinicaEntity enderecoEntity) {
        return EnderecoClinicaResponse.builder()
                .id(enderecoEntity.getId())
                .cep(enderecoEntity.getCep())
                .logradouro(enderecoEntity.getLogradouro())
                .numero(enderecoEntity.getNumero())
                .complemento(enderecoEntity.getComplemento())
                .bairro(enderecoEntity.getBairro())
                .cidade(enderecoEntity.getCidade())
                .estado(enderecoEntity.getEstado())
                .pais(enderecoEntity.getPais())
                .build();
    }

    public ClinicasAdminEntity createClinicasAdminEntity(ClinicaEntity clinicaEntity, ClinicaRequest requestClient) {
        String ipAddress = request.getRemoteAddr();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = LocalTime.now().format(timeFormatter);
        return new ClinicasAdminEntity(
                clinicaEntity.getId(),
                requestClient.getAdminId(),
                LocalDate.now(),
                LocalTime.parse(formattedTime, timeFormatter),
                ipAddress
        );
    }

    public void processarEspecialidades(ClinicaRequest requestClient, ClinicaEntity clinicaEntity) {
        try {
            List<EspecialidadeClinicaEntity> especialidadeEntities = requestClient.getEspecialidadeClinica().stream()
                    .map(especialidadeClinicaRequest -> {
                        EspecialidadeClinicaEntity especialidade = new EspecialidadeClinicaEntity();
                        especialidade.setEspecialidades(especialidadeClinicaRequest.getEspecialidades());
                        especialidade.setClinicaEntity(clinicaEntity);
                        return especialidade;
                    })
                    .collect(Collectors.toList());
            clinicaEntity.getEspecialidades().addAll(especialidadeEntities);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao processar as especialidades da clínica");
        }
    }
}