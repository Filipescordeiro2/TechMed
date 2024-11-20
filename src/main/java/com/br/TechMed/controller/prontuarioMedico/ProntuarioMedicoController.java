package com.br.TechMed.controller.prontuarioMedico;

import com.br.TechMed.dto.prontuarioMedico.*;
import com.br.TechMed.service.servicos.prontuarioMedicoService.ProntuarioMedicoService;
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

    @GetMapping("/cliente/cpf")
    public ResponseEntity<List<ProtuarioMedicoDetalhadoDTO>> getProntuarioByCpf(@RequestParam String cpf) {
        List<ProtuarioMedicoDetalhadoDTO> prontuarioMedicoDTOs = prontuarioMedicoService.findByCpf(cpf);
        if (prontuarioMedicoDTOs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prontuarioMedicoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProtuarioMedicoDetalhadoDTO>> getProntuarioById(@PathVariable Long id) {
        List<ProtuarioMedicoDetalhadoDTO> prontuarioMedicoDTOs = prontuarioMedicoService.findById(id);
        if (prontuarioMedicoDTOs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prontuarioMedicoDTOs);
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
    public ResponseEntity<List<ExamesClienteDTO>> getExamesByProfissionalAndDataConsulta(@RequestParam Long profissionalId, @RequestParam LocalDate dataConsulta) {
        List<ExamesClienteDTO> exames = prontuarioMedicoService.findExamesByProfissionalAndDataConsulta(profissionalId, dataConsulta);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/procedimentosPorProfissionalEData")
    public ResponseEntity<List<ProcedimentosClienteDTO>> getProcedimentosByProfissionalAndDataConsulta(@RequestParam Long profissionalId, @RequestParam LocalDate dataConsulta) {
        List<ProcedimentosClienteDTO> procedimentos = prontuarioMedicoService.findProcedimentosByProfissionalAndDataConsulta(profissionalId, dataConsulta);
        return ResponseEntity.ok(procedimentos);
    }

    @GetMapping("/medicamentosPorProfissionalEData")
    public ResponseEntity<List<MedicamentosClienteDTO>> getMedicamentosByProfissionalAndDataConsulta(@RequestParam Long profissionalId, @RequestParam LocalDate dataConsulta) {
        List<MedicamentosClienteDTO> medicamentos = prontuarioMedicoService.findMedicamentosByProfissionalAndDataConsulta(profissionalId, dataConsulta);
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