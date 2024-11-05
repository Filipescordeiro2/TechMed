package com.br.TechMed.service.servicos.clinica;

import com.br.TechMed.dto.Clinica.ProfissionaisClinicaDTO;
import com.br.TechMed.dto.Clinica.ProfissionaisClinicaDetalhadoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfissionaisClinicaService {
    ProfissionaisClinicaDTO save(ProfissionaisClinicaDTO profissionaisClinicaDTO);
    List<ProfissionaisClinicaDTO> findByProfissionalId(Long profissionalId);
    List<ProfissionaisClinicaDTO> findByClinicaId(Long clinicaId);
    List<ProfissionaisClinicaDetalhadoDTO>findByProfissionalIdDetalhada(Long profissionalId);
    List<ProfissionaisClinicaDetalhadoDTO>findByClinicaIdDetalhada(Long clinicaId);

}
