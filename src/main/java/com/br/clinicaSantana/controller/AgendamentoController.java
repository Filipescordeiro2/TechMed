package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.AgendamentoDTO;
import com.br.clinicaSantana.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(@RequestBody AgendamentoDTO agendamentoDTO) {
        AgendamentoDTO novoAgendamento = agendamentoService.criarAgendamento(agendamentoDTO);
        return ResponseEntity.ok(novoAgendamento);
    }
}