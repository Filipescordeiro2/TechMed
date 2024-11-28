package com.br.TechMed.controller.agendamento;

import com.br.TechMed.dto.agendamento.AgendamentoDTO;
import com.br.TechMed.dto.agendamento.AgendamentoDetalhadaDTO;
import com.br.TechMed.service.servicos.agendamento.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/detalhado")
    public ResponseEntity<List<AgendamentoDetalhadaDTO>> getAgendamentoDetalhado(
            @RequestParam Long agendaId) {
        List<AgendamentoDetalhadaDTO> agendamentoDetalhado = agendamentoService.getAgendamentoDetalhado(agendaId);
        return ResponseEntity.ok(agendamentoDetalhado);
    }

    @GetMapping("/detalhadoPorCpf")
    public ResponseEntity<List<AgendamentoDetalhadaDTO>> getAgendamentoDetalhadoPorCpf(
            @RequestParam String cpf) {
        List<AgendamentoDetalhadaDTO> agendamentoDetalhado = agendamentoService.getAgendamentoDetalhadoPorCpf(cpf);
        return ResponseEntity.ok(agendamentoDetalhado);
    }

}