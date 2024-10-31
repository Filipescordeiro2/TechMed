package com.br.clinicaSantana.service;

import com.br.clinicaSantana.dto.AgendaDTO;
import com.br.clinicaSantana.dto.AgendaDetalhadaDTO;
import com.br.clinicaSantana.dto.LoginSenhaProfissionalDTO;
import com.br.clinicaSantana.dto.ProfissionalDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public interface ProfissionalService {

    ProfissionalDTO cadastrarProfissional(ProfissionalDTO profissionalDTO);
    ProfissionalDTO autenticarProfissional(LoginSenhaProfissionalDTO loginSenhaProfissionalDTO);
    List<AgendaDetalhadaDTO> getAgendaByProfissional(Long profissionalId, String statusAgenda, LocalDate data, LocalTime hora, String nomeProfissional);

}
