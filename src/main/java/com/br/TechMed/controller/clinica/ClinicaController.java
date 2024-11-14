package com.br.TechMed.controller.clinica;


import com.br.TechMed.dto.Clinica.ClinicaDTO;
import com.br.TechMed.dto.Clinica.EnderecoClinicaDTO;
import com.br.TechMed.service.servicos.clinica.ClinicaService;
import com.br.TechMed.service.servicos.clinica.EnderecoClinicaService;
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

    @Autowired
    private EnderecoClinicaService enderecoClinicaService;

    @PostMapping
    public ClinicaDTO cadastrarClinica(@RequestBody ClinicaDTO clinicaDTO) {
        return clinicaService.cadastrarClinica(clinicaDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClinicaDTO>> listarTodasClinicas() {
        List<ClinicaDTO> clinicas = clinicaService.listarTodasClinicas();
        return ResponseEntity.ok(clinicas);
    }

    /**
     * Retorna a quantidade de clinica cadastrados.
     *
     * @return a quantidade de clinica cadastrados
     */
    @GetMapping("/contar")
    public ResponseEntity<Long> contarClinicas() {
        long quantidadeClinicas = clinicaService.contarClinicas();
        return ResponseEntity.ok(quantidadeClinicas);
    }

    @PatchMapping("/inativarClinica/{id}")
    public void updateStatus(@PathVariable("id") Long id) {
        clinicaService.atualizarStatusClinica(id);
    }

    @GetMapping("/endereco")
    public ResponseEntity<EnderecoClinicaDTO> buscarEnderecoPorClinicaId(@RequestParam Long clinicaId) {
        EnderecoClinicaDTO enderecoClinicaDTO = enderecoClinicaService.buscarEnderecoPorClinicaId(clinicaId);
        return ResponseEntity.ok(enderecoClinicaDTO);
    }
}
