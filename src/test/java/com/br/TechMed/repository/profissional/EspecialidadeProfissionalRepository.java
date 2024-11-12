package com.br.TechMed.repository.profissional;

import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialidadeProfissionalRepository extends JpaRepository<EspecialidadeProfissionalEntity, Long> {
    List<EspecialidadeProfissionalEntity> findByProfissionalEntityId(Long profissionalId);
}