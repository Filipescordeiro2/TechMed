package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.dto.ClinicaDTO;
import com.br.clinicaSantana.dto.EnderecoClinicaDTO;
import com.br.clinicaSantana.dto.EspecialidadeClinicaDTO;
import com.br.clinicaSantana.dto.ProfissionaisClinicaDTO;
import com.br.clinicaSantana.entity.ClinicaEntity;
import com.br.clinicaSantana.entity.EnderecoClinicaEntity;
import com.br.clinicaSantana.entity.EspecialidadeClinicaEntity;
import com.br.clinicaSantana.exception.RegraDeNegocioException;
import com.br.clinicaSantana.repository.ClinicaRepository;
import com.br.clinicaSantana.service.ClinicaService;
import com.br.clinicaSantana.service.ProfissionaisClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da interface ClinicaService.
 * Fornece métodos para gerenciar clínicas.
 */
@Service
public class ClinicaServiceImp implements ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private ProfissionaisClinicaService profissionaisClinicaService;

    /**
     * Cadastra uma nova clínica no sistema.
     *
     * @param clinicaDTO os dados da clínica a ser cadastrada
     * @return os dados da clínica cadastrada
     */
    @Override
    @Transactional
    public ClinicaDTO cadastrarClinica(ClinicaDTO clinicaDTO) {
        try {
            ClinicaEntity clinicaEntity = fromDto(clinicaDTO);

            try {
                EnderecoClinicaEntity enderecoEntity = fromDto(clinicaDTO.getEnderecoClinica());
                clinicaEntity.getEnderecos().add(enderecoEntity);
                enderecoEntity.setClinicaEntity(clinicaEntity);
            } catch (Exception e) {
                throw new RegraDeNegocioException("Erro ao processar o endereço da clínica");
            }

            try {
                List<EspecialidadeClinicaEntity> especialidadeEntities = clinicaDTO.getEspecialidadeClinica().stream()
                        .map(this::fromDto)
                        .collect(Collectors.toList());
                clinicaEntity.getEspecialidades().addAll(especialidadeEntities);
                final ClinicaEntity finalClinicaEntity = clinicaEntity;
                especialidadeEntities.forEach(especialidade -> especialidade.setClinicaEntity(finalClinicaEntity));
            } catch (Exception e) {
                throw new RegraDeNegocioException("Erro ao processar as especialidades da clínica");
            }

            clinicaEntity = clinicaRepository.save(clinicaEntity);
            return toDto(clinicaEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao cadastrar a clínica");
        }
    }

    /**
     * Converte uma entidade ClinicaEntity para um DTO ClinicaDTO.
     *
     * @param clinicaEntity a entidade da clínica
     * @return o DTO da clínica
     */
    private ClinicaDTO toDto(ClinicaEntity clinicaEntity) {
        ClinicaDTO clinicaDTO = new ClinicaDTO();
        clinicaDTO.setId(clinicaEntity.getId());
        clinicaDTO.setNomeClinica(clinicaEntity.getNomeClinica());
        clinicaDTO.setDescricaoClinica(clinicaEntity.getDescricaoClinica());
        clinicaDTO.setTelefone(clinicaEntity.getTelefone());
        clinicaDTO.setCelular(clinicaEntity.getCelular());
        clinicaDTO.setEmail(clinicaEntity.getEmail());
        clinicaDTO.setCnpj(clinicaEntity.getCnpj());

        if (!clinicaEntity.getEnderecos().isEmpty()) {
            clinicaDTO.setEnderecoClinica(toDto(clinicaEntity.getEnderecos().get(0)));
        }

        if (!clinicaEntity.getEspecialidades().isEmpty()) {
            List<EspecialidadeClinicaDTO> especialidadeDTOs = clinicaEntity.getEspecialidades().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
            clinicaDTO.setEspecialidadeClinica(especialidadeDTOs);
        }

        List<ProfissionaisClinicaDTO> profissionaisClinicaDTOs = profissionaisClinicaService.findByClinicaId(clinicaEntity.getId());
        clinicaDTO.setProfissionaisClinica(profissionaisClinicaDTOs);

        return clinicaDTO;
    }

    /**
     * Converte um DTO ClinicaDTO para uma entidade ClinicaEntity.
     *
     * @param clinicaDTO o DTO da clínica
     * @return a entidade da clínica
     */
    private ClinicaEntity fromDto(ClinicaDTO clinicaDTO) {
        ClinicaEntity clinicaEntity = new ClinicaEntity();
        clinicaEntity.setNomeClinica(clinicaDTO.getNomeClinica());
        clinicaEntity.setDescricaoClinica(clinicaDTO.getDescricaoClinica());
        clinicaEntity.setTelefone(clinicaDTO.getTelefone());
        clinicaEntity.setCelular(clinicaDTO.getCelular());
        clinicaEntity.setEmail(clinicaDTO.getEmail());
        clinicaEntity.setCnpj(clinicaDTO.getCnpj());
        return clinicaEntity;
    }

    /**
     * Converte uma entidade EnderecoClinicaEntity para um DTO EnderecoClinicaDTO.
     *
     * @param enderecoEntity a entidade do endereço da clínica
     * @return o DTO do endereço da clínica
     */
    private EnderecoClinicaDTO toDto(EnderecoClinicaEntity enderecoEntity) {
        EnderecoClinicaDTO enderecoDTO = new EnderecoClinicaDTO();
        enderecoDTO.setId(enderecoEntity.getId());
        enderecoDTO.setCep(enderecoEntity.getCep());
        enderecoDTO.setLogradouro(enderecoEntity.getLogradouro());
        enderecoDTO.setNumero(enderecoEntity.getNumero());
        enderecoDTO.setComplemento(enderecoEntity.getComplemento());
        enderecoDTO.setBairro(enderecoEntity.getBairro());
        enderecoDTO.setCidade(enderecoEntity.getCidade());
        enderecoDTO.setEstado(enderecoEntity.getEstado());
        enderecoDTO.setPais(enderecoEntity.getPais());
        return enderecoDTO;
    }

    /**
     * Converte um DTO EnderecoClinicaDTO para uma entidade EnderecoClinicaEntity.
     *
     * @param enderecoDTO o DTO do endereço da clínica
     * @return a entidade do endereço da clínica
     */
    private EnderecoClinicaEntity fromDto(EnderecoClinicaDTO enderecoDTO) {
        EnderecoClinicaEntity enderecoEntity = new EnderecoClinicaEntity();
        enderecoEntity.setCep(enderecoDTO.getCep());
        enderecoEntity.setLogradouro(enderecoDTO.getLogradouro());
        enderecoEntity.setNumero(enderecoDTO.getNumero());
        enderecoEntity.setComplemento(enderecoDTO.getComplemento());
        enderecoEntity.setBairro(enderecoDTO.getBairro());
        enderecoEntity.setCidade(enderecoDTO.getCidade());
        enderecoEntity.setEstado(enderecoDTO.getEstado());
        enderecoEntity.setPais(enderecoDTO.getPais());
        return enderecoEntity;
    }

    /**
     * Converte uma entidade EspecialidadeClinicaEntity para um DTO EspecialidadeClinicaDTO.
     *
     * @param especialidadeClinicaEntity a entidade da especialidade da clínica
     * @return o DTO da especialidade da clínica
     */
    private EspecialidadeClinicaDTO toDto(EspecialidadeClinicaEntity especialidadeClinicaEntity) {
        EspecialidadeClinicaDTO especialidadeClinicaDTO = new EspecialidadeClinicaDTO();
        especialidadeClinicaDTO.setId(especialidadeClinicaEntity.getId());
        especialidadeClinicaDTO.setEspecialidades(especialidadeClinicaEntity.getEspecialidades());
        return especialidadeClinicaDTO;
    }

    /**
     * Converte um DTO EspecialidadeClinicaDTO para uma entidade EspecialidadeClinicaEntity.
     *
     * @param especialidadeClinicaDTO o DTO da especialidade da clínica
     * @return a entidade da especialidade da clínica
     */
    private EspecialidadeClinicaEntity fromDto(EspecialidadeClinicaDTO especialidadeClinicaDTO) {
        EspecialidadeClinicaEntity especialidadeClinicaEntity = new EspecialidadeClinicaEntity();
        especialidadeClinicaEntity.setEspecialidades(especialidadeClinicaDTO.getEspecialidades());
        return especialidadeClinicaEntity;
    }
}