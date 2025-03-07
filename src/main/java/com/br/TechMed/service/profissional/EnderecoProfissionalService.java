package com.br.TechMed.service.profissional;

import com.br.TechMed.dto.request.Profissional.EnderecoProfissionalRequest;
import com.br.TechMed.dto.response.Profissional.EnderecoProfissionalResponse;
import com.br.TechMed.entity.profissional.EnderecoProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.profissional.EnderecoProfissionalRepository;
import com.br.TechMed.utils.utilitaria.ProfissionalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EnderecoProfissionalService {

    private EnderecoProfissionalRepository enderecoProfissionalRepository;
    private ProfissionalUtils profissionalUtils;

    public EnderecoProfissionalResponse salvarEndereco(EnderecoProfissionalRequest request) {
        try {
            var  enderecoEntity = new EnderecoProfissionalEntity(request);
            enderecoEntity = enderecoProfissionalRepository.save(enderecoEntity);
            return profissionalUtils.convertEnderecoResponse(enderecoEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao salvar o endereço da clínica");
        }
    }

}