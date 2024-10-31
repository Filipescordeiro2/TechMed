package com.br.clinicaSantana.service;

import com.br.clinicaSantana.dto.EnderecoClinicaDTO;
import org.springframework.stereotype.Service;

@Service
public interface EnderecoClinicaService {
    EnderecoClinicaDTO salvarEndereco(EnderecoClinicaDTO enderecoClinicaDTO);

}
