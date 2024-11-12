package com.br.TechMed.controller.clinica;


import com.br.TechMed.dto.Clinica.ClinicaDTO;
import com.br.TechMed.service.servicos.clinica.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinicas")
@CrossOrigin(origins = "http://localhost:3000") // Permite apenas o domínio do frontend

public class ClinicaController {

    @Autowired
    private ClinicaService clinicaService;

    @PostMapping
    public ClinicaDTO cadastrarClinica(@RequestBody ClinicaDTO clinicaDTO) {
        return clinicaService.cadastrarClinica(clinicaDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClinicaDTO>> listarTodasClinicas() {
        List<ClinicaDTO> clinicas = clinicaService.listarTodasClinicas();
        return ResponseEntity.ok(clinicas);
    }
}
