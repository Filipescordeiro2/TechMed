package com.br.TechMed.repository.prontuarioMedico;

import com.br.TechMed.entity.protuarioMedico.ProtuarioMedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProtuarioMedicoRepository extends JpaRepository<ProtuarioMedicoEntity,Long> {
    List<ProtuarioMedicoEntity> findByClienteId(Long clienteId);

}
