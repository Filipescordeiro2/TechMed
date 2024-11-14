package com.br.TechMed.service.imp.clinica;

import com.br.TechMed.dto.Clinica.EnderecoClinicaDTO;
import com.br.TechMed.entity.clinica.EnderecoClinicaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.clinica.EnderecoClinicaRepository;
import com.br.TechMed.service.servicos.clinica.EnderecoClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação da interface EnderecoClinicaService.
 * Fornece métodos para gerenciar endereços de clínicas.
 */
@Service
public class EnderecoClinicaServiceImp implements EnderecoClinicaService {

    @Autowired
    private EnderecoClinicaRepository enderecoClinicaRepository;

    /**
     * Salva um endereço de clínica no sistema.
     *
     * @param enderecoClinicaDTO os dados do endereço da clínica a ser salvo
     * @return os dados do endereço da clínica salvo
     */
    @Override
    public EnderecoClinicaDTO salvarEndereco(EnderecoClinicaDTO enderecoClinicaDTO) {
        try {
            EnderecoClinicaEntity enderecoEntity = fromDto(enderecoClinicaDTO);
            enderecoEntity = enderecoClinicaRepository.save(enderecoEntity);
            return toDto(enderecoEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao salvar o endereço da clínica");
        }
    }

    /**
     * Busca o endereço da clínica pelo ID da clínica.
     *
     * @param clinicaId o ID da clínica
     * @return os dados do endereço da clínica
     */
    @Override
    @Transactional
    public EnderecoClinicaDTO buscarEnderecoPorClinicaId(Long clinicaId) {
        EnderecoClinicaEntity enderecoEntity = enderecoClinicaRepository.findByClinicaEntityId(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado para a clínica com ID: " + clinicaId));
        return toDto(enderecoEntity);
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
}