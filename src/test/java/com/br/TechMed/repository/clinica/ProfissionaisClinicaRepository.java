package com.br.TechMed.repository.clinica;

import com.br.TechMed.entity.clinica.ProfissionaisClinicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável por gerenciar as operações de persistência relacionadas à entidade ProfissionaisClinicaEntity.
 */
@Repository
public interface ProfissionaisClinicaRepository extends JpaRepository<ProfissionaisClinicaEntity, Long> {
    Optional<ProfissionaisClinicaEntity> findByProfissionalIdAndClinicaEntityId(Long profissionalId, Long clinicaId);

    /**
     * Busca profissionais pelo ID do profissional.
     *
     * @param profissionalId ID do profissional.
     * @return Lista de profissionais na clínica.
     */
    List<ProfissionaisClinicaEntity> findByProfissionalId(Long profissionalId);

    /**
     * Busca profissionais pelo ID da clínica.
     *
     * @param clinicaId ID da clínica.
     * @return Lista de profissionais na clínica.
     */
    List<ProfissionaisClinicaEntity> findByClinicaEntityId(Long clinicaId);

    /**
     * Verifica se existe um profissional na clínica com o ID do profissional e o ID da clínica especificados.
     *
     * @param clinicaId ID da clínica.
     * @param profissionalId ID do profissional.
     * @return true se existir um profissional na clínica, false caso contrário.
     */
    boolean existsByClinicaEntityIdAndProfissionalId(Long clinicaId, Long profissionalId);
    boolean existsByProfissionalIdAndClinicaEntityId(Long profissionalId, Long clinicaId);
}