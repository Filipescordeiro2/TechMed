package com.br.TechMed.repository.adm;

import com.br.TechMed.entity.adm.ClinicasAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicaAdminRepository extends JpaRepository<ClinicasAdminEntity,Long> {
}
