package com.br.TechMed.repository.clinica;

import com.br.TechMed.entity.clinica.EspecialidadeClinicaEntity;
import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface EspecialidadeClinicaRepository extends JpaRepository<EspecialidadeClinicaEntity,Long> {

    List<EspecialidadeClinicaEntity> findByClinicaEntityId(Long clinicaId);

}
