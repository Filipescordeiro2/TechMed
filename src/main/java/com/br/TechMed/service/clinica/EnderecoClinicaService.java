package com.br.TechMed.service.clinica;

import com.br.TechMed.dto.request.Clinica.EnderecoClinicaRequest;
import com.br.TechMed.dto.response.Clinica.EnderecoClinicaResponse;
import com.br.TechMed.entity.clinica.EnderecoClinicaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.clinica.EnderecoClinicaRepository;
import com.br.TechMed.utils.utilitaria.ClinicaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnderecoClinicaService {

    private final EnderecoClinicaRepository enderecoClinicaRepository;
    private final ClinicaUtils clinicaUtils;

    public EnderecoClinicaResponse salvarEndereco(EnderecoClinicaRequest request) {
        try {
            var  enderecoEntity = new EnderecoClinicaEntity(request);
            enderecoEntity = enderecoClinicaRepository.save(enderecoEntity);
            return clinicaUtils.convertEnderecoResponse(enderecoEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao salvar o endereço da clínica");
        }
    }

    @Transactional
    public EnderecoClinicaResponse buscarEnderecoPorClinicaId(Long clinicaId) {
        EnderecoClinicaEntity enderecoEntity = enderecoClinicaRepository.findByClinicaEntityId(clinicaId)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado para a clínica com ID: " + clinicaId));
        return clinicaUtils.convertEnderecoResponse(enderecoEntity);
    }

}