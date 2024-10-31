package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.AgendaDetalhadaDTO;
import com.br.clinicaSantana.dto.LoginSenhaProfissionalDTO;
import com.br.clinicaSantana.dto.ProfissionalDTO;
import com.br.clinicaSantana.dto.AgendaDTO;
import com.br.clinicaSantana.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos profissionais no sistema.
 */
@RestController
@RequestMapping("/profissionais")
@CrossOrigin(origins = "http://localhost:3000") // Permite apenas o domínio do frontend
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    /**
     * Cria um novo profissional.
     *
     * @param profissionalDTO Dados do profissional a ser criado.
     * @return Dados do profissional criado.
     */
    @PostMapping
    public ResponseEntity<ProfissionalDTO> createProfissional(@RequestBody ProfissionalDTO profissionalDTO) {
        ProfissionalDTO createdProfissional = profissionalService.cadastrarProfissional(profissionalDTO);
        return ResponseEntity.ok(createdProfissional);
    }

    /**
     * Autentica um profissional pelo login e senha.
     *
     * @param loginSenhaDTO Dados de login e senha do profissional.
     * @return os dados do profissional autenticado.
     */
    @PostMapping("/autenticar")
    public ResponseEntity<ProfissionalDTO> autenticarProfissional(@RequestBody LoginSenhaProfissionalDTO loginSenhaDTO) {
        ProfissionalDTO profissionalAutenticado = profissionalService.autenticarProfissional(loginSenhaDTO);
        return ResponseEntity.ok(profissionalAutenticado);
    }

    /**
     * Recupera a agenda de um profissional.
     *
     * @param profissionalId o ID do profissional.
     * @return a lista de agendas do profissional.
     */
    @GetMapping("/agenda")
    public ResponseEntity<List<AgendaDetalhadaDTO>> getAgendaByProfissional(
            @RequestParam Long profissionalId,
            @RequestParam(required = false) String statusAgenda,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) LocalTime hora,
            @RequestParam(required = false) String nomeProfissional) {
        List<AgendaDetalhadaDTO> agenda = profissionalService.getAgendaByProfissional(profissionalId, statusAgenda, data, hora, nomeProfissional);
        return ResponseEntity.ok(agenda);
    }
}