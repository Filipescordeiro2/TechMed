package com.br.TechMed.controller.prontuarioMedico;

import com.br.TechMed.dto.prontuarioMedico.ProtuarioMedicoDTO;
import com.br.TechMed.dto.prontuarioMedico.ProtuarioMedicoDetalhadoDTO;
import com.br.TechMed.service.servicos.prontuarioMedicoService.ProntuarioMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prontuario")
@CrossOrigin(origins = "http://localhost:3000") // Permite apenas o domínio do frontend
public class ProntuarioMedicoController {

    @Autowired
    private ProntuarioMedicoService prontuarioMedicoService;

    @PostMapping
    public ResponseEntity<ProtuarioMedicoDTO> saveProntuario(@RequestBody ProtuarioMedicoDTO prontuarioMedicoDTO) {
        ProtuarioMedicoDTO savedProntuario = prontuarioMedicoService.save(prontuarioMedicoDTO);
        return ResponseEntity.ok(savedProntuario);
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<ProtuarioMedicoDetalhadoDTO>> getProntuarioByClienteId(@RequestParam Long clienteId) {
        List<ProtuarioMedicoDetalhadoDTO> prontuarioMedicoDTOs = prontuarioMedicoService.findByClienteId(clienteId);
        if (prontuarioMedicoDTOs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prontuarioMedicoDTOs);
    }
}