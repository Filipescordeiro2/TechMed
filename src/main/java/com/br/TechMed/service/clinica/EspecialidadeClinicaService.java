package com.br.TechMed.service.clinica;

import com.br.TechMed.dto.request.Clinica.EspecialidadeClinicaRequest;
import com.br.TechMed.dto.response.Clinica.EspecialidadeClinicaResponse;
import com.br.TechMed.entity.clinica.EspecialidadeClinicaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.clinica.EspecialidadeClinicaRepository;
import com.br.TechMed.utils.utilitaria.ClinicaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EspecialidadeClinicaService {

    private final EspecialidadeClinicaRepository especialidadeClinicaRepository;
    private final ClinicaUtils clinicaUtils;

    public EspecialidadeClinicaResponse salvarEspecialidade(EspecialidadeClinicaRequest request) {
        try {
            var especialidadeClinicaEntity = new EspecialidadeClinicaEntity(request);
            especialidadeClinicaEntity = especialidadeClinicaRepository.save(especialidadeClinicaEntity);
            return clinicaUtils.convertEspecialidadeResponse(especialidadeClinicaEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao salvar a especialidade da clínica");
        }
    }

    public List<EspecialidadeClinicaResponse> buscarEspecialidadePorClinicaId(Long clinicaId) {
        return especialidadeClinicaRepository.findByClinicaEntityId(clinicaId)
                .stream()
                .map(clinicaUtils::convertEspecialidadeResponse)
                .collect(Collectors.toList());
    }

}