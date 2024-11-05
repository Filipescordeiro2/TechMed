package com.br.TechMed.service.servicos.prontuarioMedicoService;

import com.br.TechMed.dto.prontuarioMedico.ProtuarioMedicoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProntuarioMedicoService {
    ProtuarioMedicoDTO save(ProtuarioMedicoDTO prontuarioMedicoDTO);
    List<ProtuarioMedicoDTO> findByClienteId(Long clienteId);

}
