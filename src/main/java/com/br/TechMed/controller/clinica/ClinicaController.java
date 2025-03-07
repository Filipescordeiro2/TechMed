package com.br.TechMed.controller.clinica;



import com.br.TechMed.dto.request.Clinica.ClinicaRequest;
import com.br.TechMed.dto.response.Clinica.ClinicaResponse;
import com.br.TechMed.dto.response.Clinica.EnderecoClinicaResponse;
import com.br.TechMed.dto.response.Clinica.EspecialidadeClinicaResponse;
import com.br.TechMed.service.clinica.ClinicaService;
import com.br.TechMed.service.clinica.EnderecoClinicaService;
import com.br.TechMed.service.clinica.EspecialidadeClinicaService;
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

    @Autowired
    private EspecialidadeClinicaService especialidadeClinicaService;

    @PostMapping
    public ClinicaResponse cadastrarClinica(@RequestBody ClinicaRequest request) {
        return clinicaService.cadastrarClinica(request);
    }

    @GetMapping
    public ResponseEntity<List<ClinicaResponse>> listarTodasClinicas() {
        List<ClinicaResponse> clinicas = clinicaService.listarTodasClinicas();
        return ResponseEntity.ok(clinicas);
    }

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
    public ResponseEntity<EnderecoClinicaResponse> buscarEnderecoPorClinicaId(@RequestParam Long clinicaId) {
        var enderecoClinicaDTO = enderecoClinicaService.buscarEnderecoPorClinicaId(clinicaId);
        return ResponseEntity.ok(enderecoClinicaDTO);
    }

    @GetMapping("/especialidadesClinica")
    public List<EspecialidadeClinicaResponse> buscarEspecialidadePorClinicaId(@RequestParam Long clinicaId) {
        return especialidadeClinicaService.buscarEspecialidadePorClinicaId(clinicaId);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ClinicaResponse>> listarClinicaAtivos() {
        List<ClinicaResponse> clinicaAtivos = clinicaService.listarClinicasAtivos();
        return ResponseEntity.ok(clinicaAtivos);
    }
}
