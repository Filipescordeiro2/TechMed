package com.br.TechMed.service.profissional;

import com.br.TechMed.Enum.StatusUsuario;

import com.br.TechMed.dto.request.Profissional.LoginSenhaProfissionalRequest;
import com.br.TechMed.dto.request.Profissional.ProfissionalRequest;
import com.br.TechMed.dto.response.Profissional.AgendaDetalhadaResponse;
import com.br.TechMed.dto.response.Profissional.ProfissionalRegisterResponse;
import com.br.TechMed.dto.response.Profissional.ProfissionalResponse;
import com.br.TechMed.entity.agenda.AgendaEntity;
import com.br.TechMed.entity.profissional.EnderecoProfissionalEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.agenda.AgendaRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import com.br.TechMed.repository.adm.ProfissionaisAdminRepository;
import com.br.TechMed.utils.utilitaria.AgendaUtils;
import com.br.TechMed.utils.utilitaria.ProfissionalUtils;
import com.br.TechMed.utils.validation.ProfissionalValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final AgendaRepository agendaRepository;
    private final ProfissionaisAdminRepository profissionaisAdminRepository;
    private final ProfissionalValidation profisisonalValidation;
    private final ProfissionalUtils profissionalUtils;
    private final AgendaUtils agendaUtils;

    @Transactional
    public ProfissionalRegisterResponse cadastrarProfissional(ProfissionalRequest requestProfisisonal) {
        try {
            profisisonalValidation.validarAdminExistente(requestProfisisonal.getAdminId());
            var profissionalEntity = new ProfissionalEntity(requestProfisisonal);
            var enderecoEntity = new EnderecoProfissionalEntity(requestProfisisonal.getEndereco());

            profissionalEntity.getEnderecos().add(enderecoEntity);
            enderecoEntity.setProfissionalEntity(profissionalEntity);

            profissionalRepository.save(profissionalEntity);
            profissionaisAdminRepository.save(profissionalUtils.createProfissionaisAdminEntity(profissionalEntity,
                    requestProfisisonal));
            log.info("Profissional cadastrada com sucesso --> {}", profissionalEntity);
            log.info("Endereco do profissional cadastrado com sucesso --> {}", enderecoEntity);

            profissionalUtils.processarEspecialidades(requestProfisisonal, profissionalEntity);
            log.info("Servico de cadastrarProfissional Finalizado");

            return profissionalUtils.convertProfissionalRegisterResponse(profissionalEntity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar o profissional: " + e.getMessage(), e);
        }
    }

    public ProfissionalResponse autenticarProfissional(LoginSenhaProfissionalRequest resquest) {
        log.info("Inicializando o servico de autenticar Profissional request --> {}", resquest);
        try {
            var profissionalEntity = profisisonalValidation.buscarProfissionalPorLogin(resquest);
            log.info("Profissional autenticado com sucesso --> {}", profissionalEntity);
            log.info("Servico de autenticarCliente Finalizado");
            return profissionalUtils.convertProfissionalResponse(profissionalEntity);
        } catch (Exception e) {
            log.error("Erro ao autenticar profissional: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao autenticar profisisonal: " + e.getMessage());
        }

    }

    public long contarProfissionais() {
        return profissionalRepository.findAll().stream()
                .filter(profissional -> profissional.getStatusProfissional() == StatusUsuario.ATIVO)
                .count();
    }


    @Transactional
    public List<AgendaDetalhadaResponse> getAgendaByProfissional(Long profissionalId, Long clinicaId, String statusAgenda,
                                                                 LocalDate data, LocalTime hora, String nomeProfissional,
                                                                 String nomeEspecialidade) {
        if (clinicaId == null) {
            throw new IllegalArgumentException("O ID da clínica não pode ser nulo");
        }

        List<AgendaEntity> agendas = agendaRepository.findByClinicaEntityId(clinicaId);
        agendas = AgendaUtils.filterAgendasByProfissional(agendas, profissionalId, profissionalRepository);
        agendas = AgendaUtils.filterAgendasByClinica(agendas, clinicaId);
        agendas = AgendaUtils.filterAgendas(agendas, data, hora, nomeProfissional, nomeEspecialidade);

        return agendas.stream()
                .map(AgendaUtils::mapToAgendaDetalhadaResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void atualizarStatusProfissional(Long id) {
        profissionalRepository.findById(id).map(profissional -> {
            profissional.setStatusProfissional(StatusUsuario.INATIVO);
            return profissionalRepository.save(profissional);
        }).orElseThrow(() -> new RegraDeNegocioException("erro ao alterar status"));
    }

    public List<ProfissionalResponse> listarProfissionaisAtivos() {
        return profissionalRepository.findAll().stream()
                .filter(profissional -> profissional.getStatusProfissional() == StatusUsuario.ATIVO)
                .map(profissionalUtils::convertProfissionalResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean isCpfDuplicado(String cpf) {
        return profissionalRepository.existsByCpf(cpf);
    }
}