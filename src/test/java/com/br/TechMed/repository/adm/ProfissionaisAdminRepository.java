package com.br.TechMed.repository.adm;

import com.br.TechMed.entity.adm.ProfissionaisAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionaisAdminRepository extends JpaRepository<ProfissionaisAdminEntity,Long> {
}
