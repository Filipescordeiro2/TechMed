package com.br.TechMed.controller.agendamento;

import com.br.TechMed.dto.agendamento.AgendamentoDTO;
import com.br.TechMed.service.servicos.agendamento.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
@CrossOrigin(origins = "http://localhost:3000") // Permite apenas o domínio do frontend
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(@RequestBody AgendamentoDTO agendamentoDTO) {
        AgendamentoDTO novoAgendamento = agendamentoService.criarAgendamento(agendamentoDTO);
        return ResponseEntity.ok(novoAgendamento);
    }
}