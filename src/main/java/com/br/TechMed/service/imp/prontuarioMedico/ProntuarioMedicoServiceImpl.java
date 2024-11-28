package com.br.TechMed.service.imp.prontuarioMedico;

import com.br.TechMed.dto.prontuarioMedico.*;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.protuarioMedico.ExamesClienteEntity;
import com.br.TechMed.entity.protuarioMedico.MedicamentosClienteEntity;
import com.br.TechMed.entity.protuarioMedico.ProcedimentosClienteEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.entity.protuarioMedico.ProtuarioMedicoEntity;
import com.br.TechMed.repository.cliente.ClienteRepository;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.prontuarioMedico.ExamesClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.MedicamentosClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ProcedimentosClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ProtuarioMedicoRepository;
import com.br.TechMed.service.servicos.prontuarioMedicoService.ProntuarioMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Autowired
    private ClinicaRepository clinicaRepository;

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

        Long clinicaId = prontuarioMedicoDTO.getClinicaId();

        ClinicaEntity clinica = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new IllegalArgumentException("Clinica não encontrada para o ID fornecido"));

        ProtuarioMedicoEntity protuarioMedico = new ProtuarioMedicoEntity();
        // Set fields from DTO to entity
        protuarioMedico.setDescricao(prontuarioMedicoDTO.getDescricao());
        protuarioMedico.setDataConsulta(prontuarioMedicoDTO.getDataConsulta());
        // Set relationships
        protuarioMedico.setCliente(cliente);
        protuarioMedico.setClinica(clinica);
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
    public long contarExames() {return examesClienteRepository.count(); }

    @Override
    public long contarProcedimentos() {
        return procedimentosClienteRepository.count();
    }

    @Override
    public long contarMedicamentos() {
        return medicamentosClienteRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProtuarioMedicoDetalhadoDTO> findByCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF do cliente é obrigatório");
        }
        Long clienteId = getClienteIdByCpf(cpf);
        return findByClienteId(clienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProtuarioMedicoDetalhadoDTO> findByClienteId(Long clienteId) {
        List<ProtuarioMedicoEntity> prontuariosMedicos = protuarioMedicoRepository.findByClienteId(clienteId);
        return prontuariosMedicos.stream().map(protuarioMedico -> {
            ProtuarioMedicoDetalhadoDTO dto = new ProtuarioMedicoDetalhadoDTO();
            dto.setId(protuarioMedico.getId());
            dto.setProfissionalId(protuarioMedico.getProfissional().getId());
            dto.setProfissionalNome(protuarioMedico.getProfissional().getNome());
            dto.setProfissionalSobrenome(protuarioMedico.getProfissional().getSobrenome());
            dto.setClienteId(protuarioMedico.getCliente().getId());
            dto.setClienteNome(protuarioMedico.getCliente().getNome());
            dto.setClienteSobrenome(protuarioMedico.getCliente().getSobrenome());
            dto.setDataNascimento(protuarioMedico.getCliente().getDataNascimento());
            dto.setIdade(protuarioMedico.getCliente().getIdade());
            dto.setClinicaId(protuarioMedico.getClinica().getId());
            dto.setClinicaNome(protuarioMedico.getClinica().getNomeClinica());
            dto.setCpf(protuarioMedico.getCliente().getCpf());
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
            dto.setObservacoes(String.valueOf(protuarioMedico.getObservacoes()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProtuarioMedicoDetalhadoDTO> findById(Long id) {
        ProtuarioMedicoEntity prontuarioMedico = protuarioMedicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prontuário não encontrado para o ID fornecido"));
        ProtuarioMedicoDetalhadoDTO dto = new ProtuarioMedicoDetalhadoDTO();
        dto.setId(prontuarioMedico.getId());
        dto.setProfissionalId(prontuarioMedico.getProfissional().getId());
        dto.setProfissionalNome(prontuarioMedico.getProfissional().getNome());
        dto.setProfissionalSobrenome(prontuarioMedico.getProfissional().getSobrenome());
        dto.setOrgaoRegulador(prontuarioMedico.getProfissional().getOrgaoRegulador());
        dto.setNumeroRegistro(prontuarioMedico.getProfissional().getNumeroRegistro());
        dto.setUfRegistro(prontuarioMedico.getProfissional().getUfOrgaoRegulador());
        dto.setClienteId(prontuarioMedico.getCliente().getId());
        dto.setClienteNome(prontuarioMedico.getCliente().getNome());
        dto.setClienteSobrenome(prontuarioMedico.getCliente().getSobrenome());
        dto.setDataNascimento(prontuarioMedico.getCliente().getDataNascimento());
        dto.setIdade(prontuarioMedico.getCliente().getIdade());
        dto.setClinicaId(prontuarioMedico.getClinica().getId());
        dto.setClinicaNome(prontuarioMedico.getClinica().getNomeClinica());
        dto.setCpf(prontuarioMedico.getCliente().getCpf());
        dto.setDescricao(prontuarioMedico.getDescricao());
        dto.setDataConsulta(prontuarioMedico.getDataConsulta());
        dto.setExames(prontuarioMedico.getExames().stream()
                .map(exame -> new ExamesClienteDTO(exame.getId(), exame.getProtuarioMedico().getId(), exame.getExame()))
                .collect(Collectors.toList()));
        dto.setProcedimentos(prontuarioMedico.getProcedimentos().stream()
                .map(procedimento -> new ProcedimentosClienteDTO(procedimento.getId(), procedimento.getProtuarioMedico().getId(), procedimento.getProcedimento()))
                .collect(Collectors.toList()));
        dto.setMedicamentos(prontuarioMedico.getMedicamentos().stream()
                .map(medicamento -> new MedicamentosClienteDTO(medicamento.getId(), medicamento.getProtuarioMedico().getId(), medicamento.getMedicamento()))
                .collect(Collectors.toList()));
        dto.setObservacoes(String.valueOf(prontuarioMedico.getObservacoes()));
        return List.of(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamesClienteDTO> findExamesByProfissionalAndDataConsulta(Long profissionalId, LocalDate dataConsulta) {
        List<ExamesClienteEntity> exames = examesClienteRepository.findByProfissionalIdAndDataConsulta(profissionalId, dataConsulta);
        return exames.stream().map(exame -> new ExamesClienteDTO(exame.getId(), exame.getProtuarioMedico().getId(), exame.getExame())).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcedimentosClienteDTO> findProcedimentosByProfissionalAndDataConsulta(Long profissionalId, LocalDate dataConsulta) {
        List<ProcedimentosClienteEntity> procedimentos = procedimentosClienteRepository.findByProfissionalIdAndDataConsulta(profissionalId, dataConsulta);
        return procedimentos.stream().map(procedimento -> new ProcedimentosClienteDTO(procedimento.getId(), procedimento.getProtuarioMedico().getId(), procedimento.getProcedimento())).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicamentosClienteDTO> findMedicamentosByProfissionalAndDataConsulta(Long profissionalId, LocalDate dataConsulta) {
        List<MedicamentosClienteEntity> medicamentos = medicamentosClienteRepository.findByProfissionalIdAndDataConsulta(profissionalId, dataConsulta);
        return medicamentos.stream().map(medicamento -> new MedicamentosClienteDTO(medicamento.getId(), medicamento.getProtuarioMedico().getId(), medicamento.getMedicamento())).collect(Collectors.toList());
    }


    @Override
    public long countExamesByProfissionalAndDataConsultaBetweenAndClinicaId(Long profissionalId, LocalDate startDate, LocalDate endDate, Long clinicaId) {
        return examesClienteRepository.countByProfissionalIdAndDataConsultaBetweenAndClinicaId(profissionalId, startDate, endDate, clinicaId);
    }

    @Override
    public long countProcedimentosByProfissionalAndDataConsultaBetweenAndClinicaId(Long profissionalId, LocalDate startDate, LocalDate endDate, Long clinicaId) {
        return procedimentosClienteRepository.countByProfissionalIdAndDataConsultaBetweenAndClinicaId(profissionalId, startDate, endDate, clinicaId);
    }

    @Override
    public long countMedicamentosByProfissionalAndDataConsultaBetweenAndClinicaId(Long profissionalId, LocalDate startDate, LocalDate endDate, Long clinicaId) {
        return medicamentosClienteRepository.countByProfissionalIdAndDataConsultaBetweenAndClinicaId(profissionalId, startDate, endDate, clinicaId);
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