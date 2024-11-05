package com.br.TechMed.entity.adm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "historico_criacao_profissionais")
public class ProfissionaisAdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_profissional", nullable = false)
    private Long profissionalId;

    @Column(name = "id_admin", nullable = false)
    private Long adminId;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @Column(name="Hora_criacao", nullable = false)
    private LocalTime horaCriacao;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;
}
