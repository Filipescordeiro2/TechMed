package com.br.TechMed.service.servicos.clinica;

import com.br.TechMed.dto.Clinica.ClinicaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClinicaService {
    ClinicaDTO cadastrarClinica(ClinicaDTO clinicaDTO);
    List<ClinicaDTO> listarTodasClinicas();

}
