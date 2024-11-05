package com.br.TechMed.service;

import com.br.TechMed.dto.Clinica.EnderecoClinicaDTO;
import org.springframework.stereotype.Service;

@Service
public interface EnderecoClinicaService {
    EnderecoClinicaDTO salvarEndereco(EnderecoClinicaDTO enderecoClinicaDTO);

}
