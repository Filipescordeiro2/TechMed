package com.br.TechMed.repository.profissional;

import com.br.TechMed.entity.profissional.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<ProfissionalEntity,Long> {
    Optional<ProfissionalEntity> findByLogin(String login);
    boolean existsByCpf(String cpf);

}
