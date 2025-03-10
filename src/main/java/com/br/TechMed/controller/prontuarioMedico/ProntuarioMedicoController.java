package com.br.TechMed.controller.prontuarioMedico;

import com.br.TechMed.dto.request.prontuarioMedico.ProtuarioMedicoRequest;
import com.br.TechMed.dto.response.ProntuarioMedico.*;
import com.br.TechMed.service.ProntuarioMedico.ProntuarioMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/prontuario")
@CrossOrigin(origins = "http://localhost:3000") // Permite apenas o domínio do frontend
public class ProntuarioMedicoController {

    @Autowired
    private ProntuarioMedicoService prontuarioMedicoService;

    @PostMapping
    public ResponseEntity<ProtuarioMedicoResponse> saveProntuario(@RequestBody ProtuarioMedicoRequest request) {
        ProtuarioMedicoResponse savedProntuario = prontuarioMedicoService.save(request);
        return ResponseEntity.ok(savedProntuario);
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<ProtuarioMedicoDetalhadoResponse>> getProntuarioByClienteId(@RequestParam Long clienteId) {
        List<ProtuarioMedicoDetalhadoResponse> prontuarioMedicoResponses = prontuarioMedicoService.findByClienteId(clienteId);
        if (prontuarioMedicoResponses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prontuarioMedicoResponses);
    }

    @GetMapping("/cliente/cpf")
    public ResponseEntity<List<ProtuarioMedicoDetalhadoResponse>> getProntuarioByCpf(@RequestParam String cpf) {
        List<ProtuarioMedicoDetalhadoResponse> prontuarioMedicoResponses = prontuarioMedicoService.findByCpf(cpf);
        if (prontuarioMedicoResponses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prontuarioMedicoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProtuarioMedicoDetalhadoResponse> getProntuarioById(@PathVariable Long id) {
        ProtuarioMedicoDetalhadoResponse prontuarioMedicoResponse = prontuarioMedicoService.findById(id);
        return ResponseEntity.ok(prontuarioMedicoResponse);
    }

    @GetMapping("/contarExames")
    public ResponseEntity<Long> contarExames() {
        long quantidadeExames = prontuarioMedicoService.contarExames();
        return ResponseEntity.ok(quantidadeExames);
    }

    @GetMapping("/contarProcedimentos")
    public ResponseEntity<Long> contarProcedimentos() {
        long quantidadeProcedimentos = prontuarioMedicoService.contarProcedimentos();
        return ResponseEntity.ok(quantidadeProcedimentos);
    }

    @GetMapping("/contarMedicamentos")
    public ResponseEntity<Long> contarMedicamentos() {
        long quantidadeMedicamentos = prontuarioMedicoService.contarMedicamentos();
        return ResponseEntity.ok(quantidadeMedicamentos);
    }

    @GetMapping("/examesPorProfissionalEData")
    public ResponseEntity<List<ExamesClienteResponse>> getExamesByProfissionalAndDataConsulta(@RequestParam Long profissionalId, @RequestParam LocalDate dataConsulta) {
        List<ExamesClienteResponse> exames = prontuarioMedicoService.findExamesByProfissionalAndDataConsulta(profissionalId, dataConsulta);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/procedimentosPorProfissionalEData")
    public ResponseEntity<List<ProcedimentosClienteResponse>> getProcedimentosByProfissionalAndDataConsulta(@RequestParam Long profissionalId, @RequestParam LocalDate dataConsulta) {
        List<ProcedimentosClienteResponse> procedimentos = prontuarioMedicoService.findProcedimentosByProfissionalAndDataConsulta(profissionalId, dataConsulta);
        return ResponseEntity.ok(procedimentos);
    }

    @GetMapping("/medicamentosPorProfissionalEData")
    public ResponseEntity<List<MedicamentosClienteResponse>> getMedicamentosByProfissionalAndDataConsulta(@RequestParam Long profissionalId, @RequestParam LocalDate dataConsulta) {
        List<MedicamentosClienteResponse> medicamentos = prontuarioMedicoService.findMedicamentosByProfissionalAndDataConsulta(profissionalId, dataConsulta);
        return ResponseEntity.ok(medicamentos);
    }

    @GetMapping("/Count/examesPorProfissionalEData")
    public ResponseEntity<Long> countExamesByProfissionalAndDataConsultaBetweenAndClinicaId(@RequestParam Long profissionalId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam Long clinicaId) {
        long count = prontuarioMedicoService.countExamesByProfissionalAndDataConsultaBetweenAndClinicaId(profissionalId, startDate, endDate, clinicaId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/Count/procedimentosPorProfissionalEData")
    public ResponseEntity<Long> countProcedimentosByProfissionalAndDataConsultaBetweenAndClinicaId(@RequestParam Long profissionalId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam Long clinicaId) {
        long count = prontuarioMedicoService.countProcedimentosByProfissionalAndDataConsultaBetweenAndClinicaId(profissionalId, startDate, endDate, clinicaId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/Count/medicamentosPorProfissionalEData")
    public ResponseEntity<Long> countMedicamentosByProfissionalAndDataConsultaBetweenAndClinicaId(@RequestParam Long profissionalId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam Long clinicaId) {
        long count = prontuarioMedicoService.countMedicamentosByProfissionalAndDataConsultaBetweenAndClinicaId(profissionalId, startDate, endDate, clinicaId);
        return ResponseEntity.ok(count);
    }
}