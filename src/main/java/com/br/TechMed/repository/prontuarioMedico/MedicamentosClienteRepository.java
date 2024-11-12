package com.br.TechMed.repository.prontuarioMedico;

import com.br.TechMed.entity.protuarioMedico.MedicamentosClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MedicamentosClienteRepository extends JpaRepository<MedicamentosClienteEntity,Long> {
    @Query("SELECT m FROM MedicamentosClienteEntity m WHERE m.profissional.id = :profissionalId AND m.protuarioMedico.dataConsulta = :dataConsulta")
    List<MedicamentosClienteEntity> findByProfissionalIdAndDataConsulta(@Param("profissionalId") Long profissionalId, @Param("dataConsulta") LocalDate dataConsulta);

    @Query("SELECT COUNT(m) FROM MedicamentosClienteEntity m WHERE m.profissional.id = :profissionalId AND m.protuarioMedico.dataConsulta BETWEEN :startDate AND :endDate AND m.protuarioMedico.clinica.id = :clinicaId")
    long countByProfissionalIdAndDataConsultaBetweenAndClinicaId(@Param("profissionalId") Long profissionalId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("clinicaId") Long clinicaId);


}
