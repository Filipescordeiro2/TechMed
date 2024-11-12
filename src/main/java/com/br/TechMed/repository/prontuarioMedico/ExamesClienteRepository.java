package com.br.TechMed.repository.prontuarioMedico;

import com.br.TechMed.entity.protuarioMedico.ExamesClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExamesClienteRepository extends JpaRepository<ExamesClienteEntity,Long> {
    @Query("SELECT e FROM ExamesClienteEntity e WHERE e.profissional.id = :profissionalId AND e.protuarioMedico.dataConsulta = :dataConsulta")
    List<ExamesClienteEntity> findByProfissionalIdAndDataConsulta(@Param("profissionalId") Long profissionalId, @Param("dataConsulta") LocalDate dataConsulta);
    @Query("SELECT COUNT(e) FROM ExamesClienteEntity e WHERE e.profissional.id = :profissionalId AND e.protuarioMedico.dataConsulta BETWEEN :startDate AND :endDate AND e.protuarioMedico.clinica.id = :clinicaId")
    long countByProfissionalIdAndDataConsultaBetweenAndClinicaId(@Param("profissionalId") Long profissionalId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("clinicaId") Long clinicaId);


}
