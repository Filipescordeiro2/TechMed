package com.br.TechMed.service.imp.prontuarioMedico;

import com.br.TechMed.dto.prontuarioMedico.ExamesClienteDTO;
import com.br.TechMed.dto.prontuarioMedico.MedicamentosClienteDTO;
import com.br.TechMed.dto.prontuarioMedico.ProcedimentosClienteDTO;
import com.br.TechMed.dto.prontuarioMedico.ProtuarioMedicoDTO;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.protuarioMedico.ExamesClienteEntity;
import com.br.TechMed.entity.protuarioMedico.MedicamentosClienteEntity;
import com.br.TechMed.entity.protuarioMedico.ProcedimentosClienteEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.entity.protuarioMedico.ProtuarioMedicoEntity;
import com.br.TechMed.repository.cliente.ClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ExamesClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.MedicamentosClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ProcedimentosClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ProtuarioMedicoRepository;
import com.br.TechMed.service.servicos.prontuarioMedicoService.ProntuarioMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProntuarioMedicoServiceImpl implements ProntuarioMedicoService {

    @Autowired
    private ProtuarioMedicoRepository protuarioMedicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ExamesClienteRepository examesClienteRepository;

    @Autowired
    private ProcedimentosClienteRepository procedimentosClienteRepository;

    @Autowired
    private MedicamentosClienteRepository medicamentosClienteRepository;

    private Long getClienteIdByCpf(String cpf) {
        ClienteEntity cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o CPF fornecido"));
        return cliente.getId();
    }

    @Override
    @Transactional
    public ProtuarioMedicoDTO save(ProtuarioMedicoDTO prontuarioMedicoDTO) {
        // Verifique se o CPF está presente
        if (prontuarioMedicoDTO.getCpf() == null || prontuarioMedicoDTO.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF do cliente é obrigatório");
        }

        // Recupere o clienteId pelo CPF
        Long clienteId = getClienteIdByCpf(prontuarioMedicoDTO.getCpf());

        // Busque o cliente pelo clienteId
        ClienteEntity cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o ID fornecido"));

        ProtuarioMedicoEntity protuarioMedico = new ProtuarioMedicoEntity();
        // Set fields from DTO to entity
        protuarioMedico.setDescricao(prontuarioMedicoDTO.getDescricao());
        protuarioMedico.setDataConsulta(prontuarioMedicoDTO.getDataConsulta());
        // Set relationships
        protuarioMedico.setCliente(cliente);
        protuarioMedico.setProfissional(new ProfissionalEntity(prontuarioMedicoDTO.getProfissionalId()));

        // Save ProtuarioMedico
        protuarioMedico = protuarioMedicoRepository.save(protuarioMedico);

        // Save related entities and update DTOs
        prontuarioMedicoDTO.setExames(saveExames(prontuarioMedicoDTO.getExames(), protuarioMedico));
        prontuarioMedicoDTO.setProcedimentos(saveProcedimentos(prontuarioMedicoDTO.getProcedimentos(), protuarioMedico));
        prontuarioMedicoDTO.setMedicamentos(saveMedicamentos(prontuarioMedicoDTO.getMedicamentos(), protuarioMedico));

        // Convert back to DTO
        prontuarioMedicoDTO.setId(protuarioMedico.getId());
        prontuarioMedicoDTO.setClienteId(clienteId);
        return prontuarioMedicoDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProtuarioMedicoDTO> findByClienteId(Long clienteId) {
        List<ProtuarioMedicoEntity> prontuariosMedicos = protuarioMedicoRepository.findByClienteId(clienteId);
        return prontuariosMedicos.stream().map(protuarioMedico -> {
            ProtuarioMedicoDTO dto = new ProtuarioMedicoDTO();
            dto.setId(protuarioMedico.getId());
            dto.setProfissionalId(protuarioMedico.getProfissional().getId());
            dto.setCpf(protuarioMedico.getCliente().getCpf()); // Set CPF instead of clienteId
            dto.setDescricao(protuarioMedico.getDescricao());
            dto.setDataConsulta(protuarioMedico.getDataConsulta());
            dto.setExames(protuarioMedico.getExames().stream()
                .map(exame -> new ExamesClienteDTO(exame.getId(), exame.getProtuarioMedico().getId(), exame.getExame()))
                .collect(Collectors.toList()));
            dto.setProcedimentos(protuarioMedico.getProcedimentos().stream()
                .map(procedimento -> new ProcedimentosClienteDTO(procedimento.getId(), procedimento.getProtuarioMedico().getId(), procedimento.getProcedimento()))
                .collect(Collectors.toList()));
            dto.setMedicamentos(protuarioMedico.getMedicamentos().stream()
                .map(medicamento -> new MedicamentosClienteDTO(medicamento.getId(), medicamento.getProtuarioMedico().getId(), medicamento.getMedicamento()))
                .collect(Collectors.toList()));
            dto.setObservacoes(protuarioMedico.getObservacoes());
            return dto;
        }).collect(Collectors.toList());
    }

    private List<ExamesClienteDTO> saveExames(List<ExamesClienteDTO> examesDTO, ProtuarioMedicoEntity protuarioMedico) {
        List<ExamesClienteEntity> exames = examesDTO.stream().map(dto -> {
            ExamesClienteEntity entity = new ExamesClienteEntity();
            entity.setProtuarioMedico(protuarioMedico);
            entity.setCliente(protuarioMedico.getCliente());
            entity.setProfissional(protuarioMedico.getProfissional());
            entity.setExame(dto.getExame());
            return entity;
        }).collect(Collectors.toList());
        exames = examesClienteRepository.saveAll(exames);
        return exames.stream().map(entity -> new ExamesClienteDTO(entity.getId(), entity.getProtuarioMedico().getId(), entity.getExame())).collect(Collectors.toList());
    }

    private List<ProcedimentosClienteDTO> saveProcedimentos(List<ProcedimentosClienteDTO> procedimentosDTO, ProtuarioMedicoEntity protuarioMedico) {
        List<ProcedimentosClienteEntity> procedimentos = procedimentosDTO.stream().map(dto -> {
            ProcedimentosClienteEntity entity = new ProcedimentosClienteEntity();
            entity.setProtuarioMedico(protuarioMedico);
            entity.setCliente(protuarioMedico.getCliente());
            entity.setProfissional(protuarioMedico.getProfissional());
            entity.setProcedimento(dto.getProcedimento());
            return entity;
        }).collect(Collectors.toList());
        procedimentos = procedimentosClienteRepository.saveAll(procedimentos);
        return procedimentos.stream().map(entity -> new ProcedimentosClienteDTO(entity.getId(), entity.getProtuarioMedico().getId(), entity.getProcedimento())).collect(Collectors.toList());
    }

    private List<MedicamentosClienteDTO> saveMedicamentos(List<MedicamentosClienteDTO> medicamentosDTO, ProtuarioMedicoEntity protuarioMedico) {
        List<MedicamentosClienteEntity> medicamentos = medicamentosDTO.stream().map(dto -> {
            MedicamentosClienteEntity entity = new MedicamentosClienteEntity();
            entity.setProtuarioMedico(protuarioMedico);
            entity.setCliente(protuarioMedico.getCliente());
            entity.setProfissional(protuarioMedico.getProfissional());
            entity.setMedicamento(dto.getMedicamento());
            return entity;
        }).collect(Collectors.toList());
        medicamentos = medicamentosClienteRepository.saveAll(medicamentos);
        return medicamentos.stream().map(entity -> new MedicamentosClienteDTO(entity.getId(), entity.getProtuarioMedico().getId(), entity.getMedicamento())).collect(Collectors.toList());
    }
}