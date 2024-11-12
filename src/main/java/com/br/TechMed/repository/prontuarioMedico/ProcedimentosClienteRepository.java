package com.br.TechMed.repository.prontuarioMedico;

import com.br.TechMed.entity.protuarioMedico.ProcedimentosClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProcedimentosClienteRepository extends JpaRepository<ProcedimentosClienteEntity,Long> {
    @Query("SELECT p FROM ProcedimentosClienteEntity p WHERE p.profissional.id = :profissionalId AND p.protuarioMedico.dataConsulta = :dataConsulta")
    List<ProcedimentosClienteEntity> findByProfissionalIdAndDataConsulta(@Param("profissionalId") Long profissionalId, @Param("dataConsulta") LocalDate dataConsulta);

    @Query("SELECT COUNT(p) FROM ProcedimentosClienteEntity p WHERE p.profissional.id = :profissionalId AND p.protuarioMedico.dataConsulta BETWEEN :startDate AND :endDate AND p.protuarioMedico.clinica.id = :clinicaId")
    long countByProfissionalIdAndDataConsultaBetweenAndClinicaId(@Param("profissionalId") Long profissionalId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("clinicaId") Long clinicaId);

}
