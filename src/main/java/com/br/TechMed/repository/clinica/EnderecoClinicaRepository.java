package com.br.TechMed.repository.clinica;

import com.br.TechMed.entity.clinica.EnderecoClinicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoClinicaRepository extends JpaRepository<EnderecoClinicaEntity,Long> {

    Optional<EnderecoClinicaEntity> findByClinicaEntityId(Long clinicaId);
}
