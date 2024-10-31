package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.dto.EnderecoProfissionalDTO;
import com.br.clinicaSantana.entity.EnderecoProfissionalEntity;
import com.br.clinicaSantana.exception.RegraDeNegocioException;
import com.br.clinicaSantana.repository.EnderecoProfissionalRepository;
import com.br.clinicaSantana.service.EnderecoProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementação da interface EnderecoProfissionalService.
 * Fornece métodos para gerenciar endereços de profissionais.
 */
@Service
public class EnderecoProfissionalServiceImp implements EnderecoProfissionalService {

    @Autowired
    private EnderecoProfissionalRepository enderecoProfissionalRepository;

    /**
     * Salva um endereço de profissional no sistema.
     *
     * @param enderecoProfissionalDTO os dados do endereço do profissional a ser salvo
     * @return os dados do endereço do profissional salvo
     */
    @Override
    public EnderecoProfissionalDTO salvarEndereco(EnderecoProfissionalDTO enderecoProfissionalDTO) {
        try {
            EnderecoProfissionalEntity enderecoEntity = fromDto(enderecoProfissionalDTO);
            enderecoEntity = enderecoProfissionalRepository.save(enderecoEntity);
            return toDto(enderecoEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao salvar o endereço do cliente");
        }
    }

    /**
     * Converte uma entidade EnderecoProfissionalEntity para um DTO EnderecoProfissionalDTO.
     *
     * @param enderecoEntity a entidade do endereço do profissional
     * @return o DTO do endereço do profissional
     */
    private EnderecoProfissionalDTO toDto(EnderecoProfissionalEntity enderecoEntity) {
        EnderecoProfissionalDTO enderecoDTO = new EnderecoProfissionalDTO();
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
     * Converte um DTO EnderecoProfissionalDTO para uma entidade EnderecoProfissionalEntity.
     *
     * @param enderecoDTO o DTO do endereço do profissional
     * @return a entidade do endereço do profissional
     */
    private EnderecoProfissionalEntity fromDto(EnderecoProfissionalDTO enderecoDTO) {
        EnderecoProfissionalEntity enderecoEntity = new EnderecoProfissionalEntity();
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