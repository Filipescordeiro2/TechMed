package com.br.TechMed.repository.prontuarioMedico;

import com.br.TechMed.entity.protuarioMedico.ProcedimentosClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentosClienteRepository extends JpaRepository<ProcedimentosClienteEntity,Long> {
}
