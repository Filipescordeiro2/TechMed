package com.br.TechMed.controller.profissional;

import com.br.TechMed.dto.request.Clinica.ProfissionaisClinicaRequest;
import com.br.TechMed.dto.response.Clinica.ProfissionaisClinicaDetalhadoResponse;
import com.br.TechMed.dto.response.Clinica.ProfissionaisClinicaResponse;
import com.br.TechMed.service.clinica.ProfissionaisClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais-clinica")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfissionaisClinicaController {

    @Autowired
    private ProfissionaisClinicaService profissionaisClinicaService;


    @PostMapping
    public ResponseEntity<ProfissionaisClinicaResponse> create(@RequestBody ProfissionaisClinicaRequest request) {
        var savedProfissionaisClinica = profissionaisClinicaService.save(request);
        return ResponseEntity.ok(savedProfissionaisClinica);
    }


    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<ProfissionaisClinicaResponse>> getByProfissionalId(@PathVariable Long profissionalId) {
        List<ProfissionaisClinicaResponse> profissionaisClinicaList = profissionaisClinicaService.findByProfissionalId(profissionalId);
        return ResponseEntity.ok(profissionaisClinicaList);
    }


    @GetMapping("/clinica/{clinicaId}")
    public ResponseEntity<List<ProfissionaisClinicaResponse>> getByClinicaId(@PathVariable Long clinicaId) {
        List<ProfissionaisClinicaResponse> profissionaisClinicaList = profissionaisClinicaService.findByClinicaId(clinicaId);
        return ResponseEntity.ok(profissionaisClinicaList);
    }


    @GetMapping("/profissional-detalhado")
    public ResponseEntity<List<ProfissionaisClinicaDetalhadoResponse>> getByProfissionalIdDetalhado(@RequestParam Long profissionalId) {
        List<ProfissionaisClinicaDetalhadoResponse> profissionaisClinicaDetalhadoList = profissionaisClinicaService.findByProfissionalIdDetalhada(profissionalId);
        return ResponseEntity.ok(profissionaisClinicaDetalhadoList);
    }


    @GetMapping("/clinica-detalhado")
    public ResponseEntity<List<ProfissionaisClinicaDetalhadoResponse>> findByClinicaIdDetalhada(@RequestParam Long clinicaId) {
        List<ProfissionaisClinicaDetalhadoResponse> profissionaisClinicaDetalhadoList = profissionaisClinicaService.findByClinicaIdDetalhada(clinicaId);
        return ResponseEntity.ok(profissionaisClinicaDetalhadoList);
    }

}