package com.br.TechMed.repository.prontuarioMedico;

import com.br.TechMed.entity.protuarioMedico.MedicamentosClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentosClienteRepository extends JpaRepository<MedicamentosClienteEntity,Long> {
}
