package com.br.clinicaSantana.repository;

import com.br.clinicaSantana.entity.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<ProfissionalEntity,Long> {
    Optional<ProfissionalEntity> findByLogin(String login);

}
