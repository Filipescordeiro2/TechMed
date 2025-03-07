package com.br.TechMed.utils.utilitaria;

import com.br.TechMed.dto.request.Profissional.ProfissionalRequest;
import com.br.TechMed.dto.response.Profissional.EnderecoProfissionalResponse;
import com.br.TechMed.dto.response.Profissional.EspecialidadeProfissionalResponse;
import com.br.TechMed.dto.response.Profissional.ProfissionalRegisterResponse;
import com.br.TechMed.dto.response.Profissional.ProfissionalResponse;
import com.br.TechMed.entity.adm.ProfissionaisAdminEntity;
import com.br.TechMed.entity.profissional.EnderecoProfissionalEntity;
import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
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
public class ProfissionalUtils {

    private final ProfissionaisClinicaService profissionaisClinicaService;
    private final HttpServletRequest request;

    public ProfissionalResponse convertProfissionalResponse(ProfissionalEntity profissionalEntity) {
        ProfissionalResponse response = ProfissionalResponse.builder()
                .id(profissionalEntity.getId())
                .nome(profissionalEntity.getNome())
                .sobrenome(profissionalEntity.getSobrenome())
                .celular(profissionalEntity.getCelular())
                .email(profissionalEntity.getEmail())
                .cpf(profissionalEntity.getCpf())
                .StatusProfissional(profissionalEntity.getStatusProfissional())
                .tipoUsuario(profissionalEntity.getTipoUsuario())
                .orgaoRegulador(profissionalEntity.getOrgaoRegulador())
                .numeroRegistro(profissionalEntity.getNumeroRegistro())
                .ufOrgaoRegulador(profissionalEntity.getUfOrgaoRegulador())
                .enderecoProfissional(convertEnderecoResponse(profissionalEntity.getEnderecos().get(0)))
                .especialidadesProfisisonal(profissionalEntity.getEspecialidades().stream()
                        .map(this::convertEspecialidadeResponse)
                        .collect(Collectors.toList()))
                .profissionaisClinica(profissionaisClinicaService.findByClinicaId(profissionalEntity.getId()))
                .build();
        return response;
    }

    public ProfissionalRegisterResponse convertProfissionalRegisterResponse(ProfissionalEntity profissionalEntity) {
        ProfissionalRegisterResponse response = ProfissionalRegisterResponse.builder()
                .message("Profissional cadastrado com sucesso")
                .nome(profissionalEntity.getNome())
                .sobrenome(profissionalEntity.getSobrenome())
                .cpf(profissionalEntity.getCpf())
                .especialidadesProfisisonal(profissionalEntity.getEspecialidades().stream()
                        .map(this::convertEspecialidadeResponse)
                        .collect(Collectors.toList()))
                .profissionaisClinica(profissionaisClinicaService.findByClinicaId(profissionalEntity.getId()))
                .build();
        return response;
    }

    public EspecialidadeProfissionalResponse convertEspecialidadeResponse(EspecialidadeProfissionalEntity especialidadeProfissionalEntity) {
        return EspecialidadeProfissionalResponse.builder()
                .especialidades(especialidadeProfissionalEntity.getEspecialidades())
                .build();
    }

    public EnderecoProfissionalResponse convertEnderecoResponse(EnderecoProfissionalEntity enderecoEntity) {
        return EnderecoProfissionalResponse.builder()
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

    public ProfissionaisAdminEntity createProfissionaisAdminEntity(ProfissionalEntity profissionalEntity, ProfissionalRequest requestProfissional) {
        String ipAddress = request.getRemoteAddr();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = LocalTime.now().format(timeFormatter);
        return new ProfissionaisAdminEntity(
                profissionalEntity.getId(),
                requestProfissional.getAdminId(),
                LocalDate.now(),
                LocalTime.parse(formattedTime, timeFormatter),
                ipAddress
        );
    }

    public void processarEspecialidades(ProfissionalRequest requestProfissional, ProfissionalEntity profissionalEntity) {
        try {
            List<EspecialidadeProfissionalEntity> especialidadeEntities = requestProfissional.getEspecialidades().stream()
                    .map(especialidadeClinicaRequest -> {
                        EspecialidadeProfissionalEntity especialidade = new EspecialidadeProfissionalEntity();
                        especialidade.setEspecialidades(especialidadeClinicaRequest.getEspecialidades());
                        especialidade.setProfissionalEntity(profissionalEntity);
                        return especialidade;
                    })
                    .collect(Collectors.toList());
            profissionalEntity.getEspecialidades().addAll(especialidadeEntities);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao processar as especialidades do profissional");
        }
    }
}
