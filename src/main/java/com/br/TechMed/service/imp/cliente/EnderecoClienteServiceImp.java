package com.br.TechMed.service.imp.cliente;

import com.br.TechMed.dto.Clinica.EnderecoClienteDTO;
import com.br.TechMed.entity.cliente.EnderecoClienteEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.cliente.EnderecoClienteRepository;
import com.br.TechMed.service.servicos.cliente.EnderecoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementação da interface EnderecoClienteService.
 * Fornece métodos para gerenciar endereços de clientes.
 */
@Service
public class EnderecoClienteServiceImp implements EnderecoClienteService {

    @Autowired
    private EnderecoClienteRepository enderecoClienteRepository;

    /**
     * Salva um endereço de cliente no sistema.
     *
     * @param enderecoClienteDTO os dados do endereço do cliente a ser salvo
     * @return os dados do endereço do cliente salvo
     */
    @Override
    public EnderecoClienteDTO salvarEndereco(EnderecoClienteDTO enderecoClienteDTO) {
        try {
            EnderecoClienteEntity enderecoEntity = fromDto(enderecoClienteDTO);
            enderecoEntity = enderecoClienteRepository.save(enderecoEntity);
            return toDto(enderecoEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao salvar o endereço do cliente");
        }
    }

    /**
     * Converte uma entidade EnderecoClienteEntity para um DTO EnderecoClienteDTO.
     *
     * @param enderecoEntity a entidade do endereço do cliente
     * @return o DTO do endereço do cliente
     */
    private EnderecoClienteDTO toDto(EnderecoClienteEntity enderecoEntity) {
        EnderecoClienteDTO enderecoDTO = new EnderecoClienteDTO();
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
     * Converte um DTO EnderecoClienteDTO para uma entidade EnderecoClienteEntity.
     *
     * @param enderecoDTO o DTO do endereço do cliente
     * @return a entidade do endereço do cliente
     */
    private EnderecoClienteEntity fromDto(EnderecoClienteDTO enderecoDTO) {
        EnderecoClienteEntity enderecoEntity = new EnderecoClienteEntity();
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