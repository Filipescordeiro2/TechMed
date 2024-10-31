package com.br.clinicaSantana.repository;

import com.br.clinicaSantana.entity.EspecialidadeClinicaEntity;
import com.br.clinicaSantana.entity.EspecialidadeProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeProfissionalRepository extends JpaRepository<EspecialidadeProfissionalEntity,Long> {
}
