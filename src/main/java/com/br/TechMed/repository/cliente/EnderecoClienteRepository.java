package com.br.TechMed.repository.cliente;

import com.br.TechMed.entity.cliente.EnderecoClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoClienteRepository extends JpaRepository<EnderecoClienteEntity,Long> {
}
