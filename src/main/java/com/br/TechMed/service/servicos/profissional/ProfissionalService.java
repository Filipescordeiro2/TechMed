package com.br.TechMed.service.servicos.profissional;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.dto.agenda.AgendaDetalhadaDTO;
import com.br.TechMed.dto.profissional.LoginSenhaProfissionalDTO;
import com.br.TechMed.dto.profissional.ProfissionalDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public interface ProfissionalService {

    ProfissionalDTO cadastrarProfissional(ProfissionalDTO profissionalDTO);
    ProfissionalDTO autenticarProfissional(LoginSenhaProfissionalDTO loginSenhaProfissionalDTO);
    List<AgendaDetalhadaDTO> getAgendaByProfissional(Long profissionalId,Long clinicaId ,String statusAgenda, LocalDate data, LocalTime hora, String nomeProfissional);
    long contarProfissionais();
    void atualizarStatusProfissional(Long id);
}
