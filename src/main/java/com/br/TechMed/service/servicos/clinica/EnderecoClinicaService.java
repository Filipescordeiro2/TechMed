package com.br.TechMed.service.servicos.clinica;

import com.br.TechMed.dto.Clinica.EnderecoClinicaDTO;
import org.springframework.stereotype.Service;

@Service
public interface EnderecoClinicaService {
    EnderecoClinicaDTO salvarEndereco(EnderecoClinicaDTO enderecoClinicaDTO);
    EnderecoClinicaDTO buscarEnderecoPorClinicaId(Long clinicaId);

}
