package com.br.TechMed.service;

import com.br.TechMed.dto.Clinica.ClinicaDTO;
import org.springframework.stereotype.Service;

@Service
public interface ClinicaService {
    ClinicaDTO cadastrarClinica(ClinicaDTO clinicaDTO);

}
