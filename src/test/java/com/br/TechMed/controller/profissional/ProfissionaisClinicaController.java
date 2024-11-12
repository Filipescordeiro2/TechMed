package com.br.TechMed.controller.profissional;

import com.br.TechMed.dto.Clinica.ProfissionaisClinicaDTO;
import com.br.TechMed.dto.Clinica.ProfissionaisClinicaDetalhadoDTO;
import com.br.TechMed.service.servicos.clinica.ProfissionaisClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos profissionais das clínicas no sistema.
 */
@RestController
@RequestMapping("/profissionais-clinica")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfissionaisClinicaController {

    @Autowired
    private ProfissionaisClinicaService profissionaisClinicaService;

    /**
     * Cria um novo registro de profissional na clínica.
     *
     * @param profissionaisClinicaDTO Dados do profissional na clínica a ser salvo.
     * @return Dados do profissional na clínica salvo.
     */
    @PostMapping
    public ResponseEntity<ProfissionaisClinicaDTO> create(@RequestBody ProfissionaisClinicaDTO profissionaisClinicaDTO) {
        ProfissionaisClinicaDTO savedProfissionaisClinica = profissionaisClinicaService.save(profissionaisClinicaDTO);
        return ResponseEntity.ok(savedProfissionaisClinica);
    }

    /**
     * Busca profissionais por ID do profissional.
     *
     * @param profissionalId ID do profissional.
     * @return Lista de profissionais na clínica.
     */
    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<ProfissionaisClinicaDTO>> getByProfissionalId(@PathVariable Long profissionalId) {
        List<ProfissionaisClinicaDTO> profissionaisClinicaList = profissionaisClinicaService.findByProfissionalId(profissionalId);
        return ResponseEntity.ok(profissionaisClinicaList);
    }

    /**
     * Busca profissionais por ID da clínica.
     *
     * @param clinicaId ID da clínica.
     * @return Lista de profissionais na clínica.
     */
    @GetMapping("/clinica/{clinicaId}")
    public ResponseEntity<List<ProfissionaisClinicaDTO>> getByClinicaId(@PathVariable Long clinicaId) {
        List<ProfissionaisClinicaDTO> profissionaisClinicaList = profissionaisClinicaService.findByClinicaId(clinicaId);
        return ResponseEntity.ok(profissionaisClinicaList);
    }

    /**
     * Busca detalhes dos profissionais por ID do profissional.
     *
     * @param profissionalId ID do profissional.
     * @return Lista detalhada de profissionais na clínica.
     */
    @GetMapping("/profissional-detalhado")
    public ResponseEntity<List<ProfissionaisClinicaDetalhadoDTO>> getByProfissionalIdDetalhado(@RequestParam Long profissionalId) {
        List<ProfissionaisClinicaDetalhadoDTO> profissionaisClinicaDetalhadoList = profissionaisClinicaService.findByProfissionalIdDetalhada(profissionalId);
        return ResponseEntity.ok(profissionaisClinicaDetalhadoList);
    }

    /**
     * Busca detalhes dos profissionais por ID do profissional.
     *
     * @param clinicaId ID do profissional.
     * @return Lista detalhada de profissionais na clínica.
     */
    @GetMapping("/clinica-detalhado")
    public ResponseEntity<List<ProfissionaisClinicaDetalhadoDTO>> findByClinicaIdDetalhada(@RequestParam Long clinicaId) {
        List<ProfissionaisClinicaDetalhadoDTO> profissionaisClinicaDetalhadoList = profissionaisClinicaService.findByClinicaIdDetalhada(clinicaId);
        return ResponseEntity.ok(profissionaisClinicaDetalhadoList);
    }


}