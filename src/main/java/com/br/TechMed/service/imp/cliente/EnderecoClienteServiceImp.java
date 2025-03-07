package com.br.TechMed.service.imp.cliente;

import com.br.TechMed.dto.request.cliente.EnderecoClienteRequest;
import com.br.TechMed.dto.response.cliente.EnderecoClienteResponse;
import com.br.TechMed.entity.cliente.EnderecoClienteEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.cliente.EnderecoClienteRepository;
import com.br.TechMed.utils.utilitaria.ClienteUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EnderecoClienteServiceImp {

    private final EnderecoClienteRepository enderecoClienteRepository;
    private final ClienteUtils clienteUtils;

    public EnderecoClienteResponse salvarEndereco(EnderecoClienteRequest request) {
        try {
            var enderecoEntity = new EnderecoClienteEntity(request);
            enderecoEntity = enderecoClienteRepository.save(enderecoEntity);
            return clienteUtils.convertEnderecoResponse(enderecoEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao salvar o endereço do cliente");
        }
    }

}