package com.br.TechMed.service.servicos.agenda;

import com.br.TechMed.Enum.Jornada;
import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.dto.agenda.AgendaDTO;
import com.br.TechMed.dto.agenda.AgendaDetalhadaDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public interface AgendaService {

    // Gera uma agenda para um profissional em uma data específica e salva no banco de dados
    List<AgendaDTO> gerarAgenda(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId);
    // Busca a agenda por profissional
    List<AgendaDTO> buscarAgendaPorProfissional(Long profissionalId);
    // Gera uma agenda para um profissional em uma data específica e salva no banco de dados
    List<AgendaDTO> gerarAgendaDaManha(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId);
    // Gera uma agenda para um profissional em uma data específica e salva no banco de dados
    List<AgendaDTO> gerarAgendaDaTarde(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId);
    // Gera uma agenda para um profissional em uma data específica e salva no banco de dados
    List<AgendaDTO> gerarAgendaDaNoite(Long profissionalId, LocalDate data, Long clinicaId, Long especialidadeId);

    void criarAgendaAvulso(Long profissionalId, LocalDate data, LocalTime hora, Long clinicaId, Long especialidadeId);
    List<AgendaDTO> buscarAgendaPorStatus(StatusAgenda status);
    List<AgendaDetalhadaDTO> buscarAgenda(Long profissionalId, Long clinicaId, String statusAgenda, LocalDate data, LocalTime hora, String nomeProfissional, Jornada periodo);
    // Altera o status da agenda para CANCELADO
    void cancelarAgenda(Long agendaId);
}