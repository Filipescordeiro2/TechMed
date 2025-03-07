package com.br.TechMed.service.clinica;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.dto.request.Clinica.ClinicaRequest;
import com.br.TechMed.dto.response.Clinica.ClinicaResponse;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.clinica.EnderecoClinicaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.adm.AdmRepository;
import com.br.TechMed.repository.adm.ClinicaAdminRepository;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.utils.utilitaria.ClinicaUtils;
import com.br.TechMed.utils.validation.ClinicaValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicaService {

    private final ClinicaRepository clinicaRepository;
    private final ClinicaAdminRepository clinicaAdminRepository;
    private final ClinicaUtils clinicaUtils;
    private final ClinicaValidation clinicaValidation;

    @Transactional
    public ClinicaResponse cadastrarClinica(ClinicaRequest requestClient) {
        log.info("Inicializando o servico de cadastrar clinica request --> {}", requestClient);
        try {

            clinicaValidation.validarAdminExistente(requestClient.getAdminId());
            var clinicaEntity = new ClinicaEntity(requestClient);
            var enderecoEntity = new EnderecoClinicaEntity(requestClient.getEnderecoClinica());

            clinicaEntity.getEnderecos().add(enderecoEntity);
            enderecoEntity.setClinicaEntity(clinicaEntity);
            clinicaRepository.save(clinicaEntity);

            clinicaAdminRepository.save(clinicaUtils.createClinicasAdminEntity(clinicaEntity, requestClient));
            log.info("Clinica cadastrada com sucesso --> {}", clinicaEntity);
            log.info("Endereco da clinica cadastrado com sucesso --> {}", enderecoEntity);

            clinicaUtils.processarEspecialidades(requestClient, clinicaEntity);
            log.info("Servico de cadastrarClinica Finalizado");

            return clinicaUtils.convertClinicaResponse(clinicaEntity);

        } catch (Exception e) {
            log.error("Erro ao cadastrar clinica: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao cadastrar clinica: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<ClinicaResponse> listarTodasClinicas() {
        List<ClinicaEntity> clinicas = clinicaRepository.findAll().stream()
                .filter(clinica -> clinica.getStatusClinica() == StatusUsuario.ATIVO)
                .collect(Collectors.toList());
        return clinicas.stream()
                .map(clinicaUtils::convertClinicaResponse)
                .collect(Collectors.toList());
    }

    public long contarClinicas() {
        return clinicaRepository.findAll().stream()
                .filter(clinica -> clinica.getStatusClinica() == StatusUsuario.ATIVO)
                .count();
    }

    @Transactional
    public void atualizarStatusClinica(Long id) {
        ClinicaEntity clinicaEntity = clinicaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));
        clinicaEntity.setStatusClinica(StatusUsuario.INATIVO);
        clinicaRepository.save(clinicaEntity);
    }

    public List<ClinicaResponse> listarClinicasAtivos() {
        return clinicaRepository.findAll().stream()
                .filter(clinica -> clinica.getStatusClinica() == StatusUsuario.ATIVO)
                .map(clinicaUtils::convertClinicaResponse)
                .collect(Collectors.toList());
    }
}