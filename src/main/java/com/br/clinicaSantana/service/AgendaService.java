package com.br.clinicaSantana.service;

import com.br.clinicaSantana.Enum.StatusAgenda;
import com.br.clinicaSantana.dto.AgendaDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public interface AgendaService {

    // Gera uma agenda para um profissional em uma data específica e salva no banco de dados
    void gerarAgenda(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId);
    // Busca a agenda por profissional
    List<AgendaDTO> buscarAgendaPorProfissional(Long profissionalId);
    // Gera uma agenda para um profissional em uma data específica e salva no banco de dados
    void gerarAgendaDaManha(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId);
    // Gera uma agenda para um profissional em uma data específica e salva no banco de dados
    void gerarAgendaDaTarde(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId);
    // Gera uma agenda para um profissional em uma data específica e salva no banco de dados
    void gerarAgendaDaNoite(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId);

    List<AgendaDTO> buscarAgenda(Optional<LocalDate> data, Optional<LocalTime> hora, Optional<String> nomeProfissional);
    void criarAgendaAvulso(Long profissionalId, LocalDate data, LocalTime hora, Long clinicaId, Long especialidadeId);
    List<AgendaDTO> buscarAgendaPorStatus(StatusAgenda status);
}