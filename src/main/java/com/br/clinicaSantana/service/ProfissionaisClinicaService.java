package com.br.clinicaSantana.service;

import com.br.clinicaSantana.dto.ProfissionaisClinicaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfissionaisClinicaService {
    ProfissionaisClinicaDTO save(ProfissionaisClinicaDTO profissionaisClinicaDTO);
    List<ProfissionaisClinicaDTO> findByProfissionalId(Long profissionalId);
    List<ProfissionaisClinicaDTO> findByClinicaId(Long clinicaId);

}
