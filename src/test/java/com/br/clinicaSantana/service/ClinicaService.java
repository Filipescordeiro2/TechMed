package com.br.clinicaSantana.service;

import com.br.clinicaSantana.dto.ClinicaDTO;
import org.springframework.stereotype.Service;

@Service
public interface ClinicaService {
    ClinicaDTO cadastrarClinica(ClinicaDTO clinicaDTO);

}
