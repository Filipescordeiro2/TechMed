package com.br.TechMed.service.ProntuarioMedico;

import com.br.TechMed.dto.request.prontuarioMedico.ProtuarioMedicoRequest;
import com.br.TechMed.dto.response.ProntuarioMedico.*;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.entity.protuarioMedico.ExamesClienteEntity;
import com.br.TechMed.entity.protuarioMedico.MedicamentosClienteEntity;
import com.br.TechMed.entity.protuarioMedico.ProcedimentosClienteEntity;
import com.br.TechMed.entity.protuarioMedico.ProtuarioMedicoEntity;
import com.br.TechMed.repository.cliente.ClienteRepository;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.prontuarioMedico.ExamesClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.MedicamentosClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ProcedimentosClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ProtuarioMedicoRepository;
import com.br.TechMed.utils.utilitaria.ProntuarioMedicoUtils;
import com.br.TechMed.utils.validation.ProntuarioMedicoValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProntuarioMedicoService {

    private final ProtuarioMedicoRepository protuarioMedicoRepository;
    private final ClienteRepository clienteRepository;
    private final ClinicaRepository clinicaRepository;
    private final ProntuarioMedicoUtils prontuarioMedicoUtils;
    private final ProntuarioMedicoValidation prontuarioMedicoValidation;
    private final ExamesClienteRepository examesClienteRepository;
    private final ProcedimentosClienteRepository procedimentosClienteRepository;
    private final MedicamentosClienteRepository medicamentosClienteRepository;

    @Transactional
    public ProtuarioMedicoResponse save(ProtuarioMedicoRequest request) {
        prontuarioMedicoValidation.validateRequest(request);

        ClienteEntity cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o ID fornecido"));

        ClinicaEntity clinica = clinicaRepository.findById(request.getClinicaId())
                .orElseThrow(() -> new IllegalArgumentException("Clinica não encontrada para o ID fornecido"));

        ProtuarioMedicoEntity protuarioMedico = new ProtuarioMedicoEntity();
        protuarioMedico.setDescricao(request.getDescricao());
        protuarioMedico.setDataConsulta(request.getDataConsulta());
        protuarioMedico.setCliente(cliente);
        protuarioMedico.setClinica(clinica);
        protuarioMedico.setProfissional(new ProfissionalEntity(request.getProfissionalId()));

        protuarioMedico = protuarioMedicoRepository.save(protuarioMedico);

        List<ExamesClienteResponse> exames = prontuarioMedicoUtils.saveExames(request.getExames(), protuarioMedico);
        List<ProcedimentosClienteResponse> procedimentos = prontuarioMedicoUtils.saveProcedimentos(request.getProcedimentos(), protuarioMedico);
        List<MedicamentosClienteResponse> medicamentos = prontuarioMedicoUtils.saveMedicamentos(request.getMedicamentos(), protuarioMedico);

        return ProtuarioMedicoResponse.builder()
                .id(protuarioMedico.getId())
                .profissionalId(request.getProfissionalId())
                .clienteId(request.getClienteId())
                .clinicaId(request.getClinicaId())
                .descricao(request.getDescricao())
                .dataConsulta(request.getDataConsulta())
                .exames(exames)
                .procedimentos(procedimentos)
                .medicamentos(medicamentos)
                .observacoes(request.getObservacoes())
                .cpf(request.getCpf())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ProtuarioMedicoDetalhadoResponse> findByCpf(String cpf) {
        prontuarioMedicoValidation.validateCpf(cpf);
        Long clienteId = prontuarioMedicoUtils.getClienteIdByCpf(cpf);
        return findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<ProtuarioMedicoDetalhadoResponse> findByClienteId(Long clienteId) {
        List<ProtuarioMedicoEntity> prontuariosMedicos = protuarioMedicoRepository.findByClienteId(clienteId);
        return prontuariosMedicos.stream().map(prontuarioMedicoUtils::convertToDetalhadoResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProtuarioMedicoDetalhadoResponse findById(Long id) {
        ProtuarioMedicoEntity prontuarioMedico = protuarioMedicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prontuário não encontrado para o ID fornecido"));
        return prontuarioMedicoUtils.convertToDetalhadoResponse(prontuarioMedico);
    }

    @Transactional(readOnly = true)
    public List<ExamesClienteResponse> findExamesByProfissionalAndDataConsulta(Long profissionalId, LocalDate dataConsulta) {
        List<ExamesClienteEntity> exames = examesClienteRepository.findByProfissionalIdAndDataConsulta(profissionalId, dataConsulta);
        return exames.stream().map(exame -> new ExamesClienteResponse(exame.getId(), exame.getProfissional().getId(), exame.getExame())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProcedimentosClienteResponse> findProcedimentosByProfissionalAndDataConsulta(Long profissionalId, LocalDate dataConsulta) {
        List<ProcedimentosClienteEntity> procedimentos = procedimentosClienteRepository.findByProfissionalIdAndDataConsulta(profissionalId, dataConsulta);
        return procedimentos.stream().map(procedimento -> new ProcedimentosClienteResponse(procedimento.getId(), procedimento.getProtuarioMedico().getId(), procedimento.getProcedimento())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MedicamentosClienteResponse> findMedicamentosByProfissionalAndDataConsulta(Long profissionalId, LocalDate dataConsulta) {
        List<MedicamentosClienteEntity> medicamentos = medicamentosClienteRepository.findByProfissionalIdAndDataConsulta(profissionalId, dataConsulta);
        return medicamentos.stream().map(medicamento -> new MedicamentosClienteResponse(medicamento.getId(), medicamento.getProtuarioMedico().getId(), medicamento.getMedicamento())).collect(Collectors.toList());
    }

    public long contarExames() {return examesClienteRepository.count(); }

    public long contarProcedimentos() {
        return procedimentosClienteRepository.count();
    }

    public long contarMedicamentos() {
        return medicamentosClienteRepository.count();
    }

    public long countExamesByProfissionalAndDataConsultaBetweenAndClinicaId(Long profissionalId, LocalDate startDate, LocalDate endDate, Long clinicaId) {
        return examesClienteRepository.countByProfissionalIdAndDataConsultaBetweenAndClinicaId(profissionalId, startDate, endDate, clinicaId);
    }

    public long countProcedimentosByProfissionalAndDataConsultaBetweenAndClinicaId(Long profissionalId, LocalDate startDate, LocalDate endDate, Long clinicaId) {
        return procedimentosClienteRepository.countByProfissionalIdAndDataConsultaBetweenAndClinicaId(profissionalId, startDate, endDate, clinicaId);
    }

    public long countMedicamentosByProfissionalAndDataConsultaBetweenAndClinicaId(Long profissionalId, LocalDate startDate, LocalDate endDate, Long clinicaId) {
        return medicamentosClienteRepository.countByProfissionalIdAndDataConsultaBetweenAndClinicaId(profissionalId, startDate, endDate, clinicaId);
    }


}
