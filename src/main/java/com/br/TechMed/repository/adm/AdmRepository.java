package com.br.TechMed.repository.adm;

import com.br.TechMed.entity.adm.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmRepository extends JpaRepository<AdminEntity,Long> {
    Optional<AdminEntity> findByLogin(String login);

}
