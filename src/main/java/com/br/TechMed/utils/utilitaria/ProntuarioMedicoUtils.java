package com.br.TechMed.utils.utilitaria;

import com.br.TechMed.dto.request.prontuarioMedico.ExamesClienteRequest;
import com.br.TechMed.dto.request.prontuarioMedico.MedicamentosClienteRequest;
import com.br.TechMed.dto.request.prontuarioMedico.ProcedimentosClienteRequest;
import com.br.TechMed.dto.response.ProntuarioMedico.ExamesClienteResponse;
import com.br.TechMed.dto.response.ProntuarioMedico.MedicamentosClienteResponse;
import com.br.TechMed.dto.response.ProntuarioMedico.ProcedimentosClienteResponse;
import com.br.TechMed.dto.response.ProntuarioMedico.ProtuarioMedicoDetalhadoResponse;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.protuarioMedico.ExamesClienteEntity;
import com.br.TechMed.entity.protuarioMedico.MedicamentosClienteEntity;
import com.br.TechMed.entity.protuarioMedico.ProcedimentosClienteEntity;
import com.br.TechMed.entity.protuarioMedico.ProtuarioMedicoEntity;
import com.br.TechMed.repository.cliente.ClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ExamesClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.MedicamentosClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ProcedimentosClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProntuarioMedicoUtils {

    private final ExamesClienteRepository examesClienteRepository;
    private final ProcedimentosClienteRepository procedimentosClienteRepository;
    private final MedicamentosClienteRepository medicamentosClienteRepository;
    private final ClienteRepository clienteRepository;

    public List<ExamesClienteResponse> saveExames(List<ExamesClienteRequest> examesDTO, ProtuarioMedicoEntity protuarioMedico) {
        List<ExamesClienteEntity> exames = examesDTO.stream().map(dto -> {
            ExamesClienteEntity entity = new ExamesClienteEntity();
            entity.setProtuarioMedico(protuarioMedico);
            entity.setCliente(protuarioMedico.getCliente());
            entity.setProfissional(protuarioMedico.getProfissional());
            entity.setExame(dto.getExame());
            return entity;
        }).collect(Collectors.toList());
        exames = examesClienteRepository.saveAll(exames);
        return exames.stream().map(entity -> new ExamesClienteResponse(entity.getId(), entity.getProtuarioMedico().getId(), entity.getExame())).collect(Collectors.toList());
    }

    public List<ProcedimentosClienteResponse> saveProcedimentos(List<ProcedimentosClienteRequest> procedimentosDTO, ProtuarioMedicoEntity protuarioMedico) {
        List<ProcedimentosClienteEntity> procedimentos = procedimentosDTO.stream().map(dto -> {
            ProcedimentosClienteEntity entity = new ProcedimentosClienteEntity();
            entity.setProtuarioMedico(protuarioMedico);
            entity.setCliente(protuarioMedico.getCliente());
            entity.setProfissional(protuarioMedico.getProfissional());
            entity.setProcedimento(dto.getProcedimento());
            return entity;
        }).collect(Collectors.toList());
        procedimentos = procedimentosClienteRepository.saveAll(procedimentos);
        return procedimentos.stream().map(entity -> new ProcedimentosClienteResponse(entity.getId(), entity.getProtuarioMedico().getId(), entity.getProcedimento())).collect(Collectors.toList());
    }

    public List<MedicamentosClienteResponse> saveMedicamentos(List<MedicamentosClienteRequest> medicamentosDTO, ProtuarioMedicoEntity protuarioMedico) {
        List<MedicamentosClienteEntity> medicamentos = medicamentosDTO.stream().map(dto -> {
            MedicamentosClienteEntity entity = new MedicamentosClienteEntity();
            entity.setProtuarioMedico(protuarioMedico);
            entity.setCliente(protuarioMedico.getCliente());
            entity.setProfissional(protuarioMedico.getProfissional());
            entity.setMedicamento(dto.getMedicamento());
            return entity;
        }).collect(Collectors.toList());
        medicamentos = medicamentosClienteRepository.saveAll(medicamentos);
        return medicamentos.stream().map(entity -> new MedicamentosClienteResponse(entity.getId(), entity.getProtuarioMedico().getId(), entity.getMedicamento())).collect(Collectors.toList());
    }

    public ProtuarioMedicoDetalhadoResponse convertToDetalhadoResponse(ProtuarioMedicoEntity protuarioMedico) {
        return ProtuarioMedicoDetalhadoResponse.builder()
                .id(protuarioMedico.getId())
                .profissionalId(protuarioMedico.getProfissional().getId())
                .profissionalNome(protuarioMedico.getProfissional().getNome())
                .profissionalSobrenome(protuarioMedico.getProfissional().getSobrenome())
                .clienteId(protuarioMedico.getCliente().getId())
                .clienteNome(protuarioMedico.getCliente().getNome())
                .clienteSobrenome(protuarioMedico.getCliente().getSobrenome())
                .dataNascimento(protuarioMedico.getCliente().getDataNascimento())
                .idade(protuarioMedico.getCliente().getIdade())
                .clinicaId(protuarioMedico.getClinica().getId())
                .clinicaNome(protuarioMedico.getClinica().getNomeClinica())
                .cpf(protuarioMedico.getCliente().getCpf())
                .descricao(protuarioMedico.getDescricao())
                .dataConsulta(protuarioMedico.getDataConsulta())
                .exames(protuarioMedico.getExames().stream()
                        .map(exame -> new ExamesClienteResponse(exame.getId(), exame.getProtuarioMedico().getId(), exame.getExame()))
                        .collect(Collectors.toList()))
                .procedimentos(protuarioMedico.getProcedimentos().stream()
                        .map(procedimento -> new ProcedimentosClienteResponse(procedimento.getId(), procedimento.getProtuarioMedico().getId(), procedimento.getProcedimento()))
                        .collect(Collectors.toList()))
                .medicamentos(protuarioMedico.getMedicamentos().stream()
                        .map(medicamento -> new MedicamentosClienteResponse(medicamento.getId(), medicamento.getProtuarioMedico().getId(), medicamento.getMedicamento()))
                        .collect(Collectors.toList()))
                .observacoes(String.valueOf(protuarioMedico.getObservacoes()))
                .numeroRegistro(protuarioMedico.getProfissional().getNumeroRegistro())
                .orgaoRegulador(protuarioMedico.getProfissional().getOrgaoRegulador())
                .ufRegistro(protuarioMedico.getProfissional().getUfOrgaoRegulador())
                .build();
    }

    public Long getClienteIdByCpf(String cpf) {
        ClienteEntity cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o CPF fornecido"));
        return cliente.getId();
    }

}
