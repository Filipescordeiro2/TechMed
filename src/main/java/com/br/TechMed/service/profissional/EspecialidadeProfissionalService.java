package com.br.TechMed.service.profissional;

import com.br.TechMed.dto.request.Profissional.EspecialidadeProfissionalRequest;
import com.br.TechMed.dto.response.Profissional.EspecialidadeProfissionalResponse;
import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.profissional.EspecialidadeProfissionalRepository;
import com.br.TechMed.utils.utilitaria.ProfissionalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EspecialidadeProfissionalService {

    private final EspecialidadeProfissionalRepository especialidadeProfissionalRepository;
    private final ProfissionalUtils profissionalUtils;

    public EspecialidadeProfissionalResponse salvarEspecialidade(EspecialidadeProfissionalRequest request) {
        try {
            var especialidadeClinicaEntity = new EspecialidadeProfissionalEntity(request);
            especialidadeClinicaEntity = especialidadeProfissionalRepository.save(especialidadeClinicaEntity);
            return profissionalUtils.convertEspecialidadeResponse(especialidadeClinicaEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao salvar a especialidade da clínica");
        }
    }

    public List<EspecialidadeProfissionalResponse> buscarEspecialidadePorProfissionalId(Long profissionalId) {
        return especialidadeProfissionalRepository.findByProfissionalEntityId(profissionalId)
                .stream()
                .map(profissionalUtils::convertEspecialidadeResponse)
                .collect(Collectors.toList());
    }


}