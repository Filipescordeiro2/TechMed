package com.br.TechMed.controller.agendamento;

import com.br.TechMed.dto.request.Agendamento.AgendamentoRequest;
import com.br.TechMed.dto.response.Agendamento.AgendamentoDetalhadaResponse;
import com.br.TechMed.dto.response.Agendamento.AgendamentoReponse;
import com.br.TechMed.service.Agendamento.AgendamentoService;
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
    public ResponseEntity<AgendamentoReponse> criarAgendamento(@RequestBody AgendamentoRequest request) {
        var novoAgendamento = agendamentoService.criarAgendamento(request);
        return ResponseEntity.ok(novoAgendamento);
    }

    @GetMapping("/detalhado")
    public ResponseEntity<List<AgendamentoDetalhadaResponse>> getAgendamentoDetalhado(
            @RequestParam Long agendaId) {
        List<AgendamentoDetalhadaResponse> agendamentoDetalhado = agendamentoService.getAgendamentoDetalhado(agendaId);
        return ResponseEntity.ok(agendamentoDetalhado);
    }

    @GetMapping("/detalhadoPorCpf")
    public ResponseEntity<List<AgendamentoDetalhadaResponse>> getAgendamentoDetalhadoPorCpf(
            @RequestParam String cpf) {
        List<AgendamentoDetalhadaResponse> agendamentoDetalhado = agendamentoService.getAgendamentoDetalhadoPorCpf(cpf);
        return ResponseEntity.ok(agendamentoDetalhado);
    }

}