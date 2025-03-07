package com.br.TechMed.controller.agenda;

import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.dto.request.Agenda.AgendaRequest;
import com.br.TechMed.dto.response.Agenda.AgendaResponse;
import com.br.TechMed.service.agenda.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("agendas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AgendaController {

    private final AgendaService agendaService;


    @PostMapping("/gerar")
    public ResponseEntity<List<AgendaResponse>> gerarAgenda(@Valid @RequestBody AgendaRequest agendaRequest) {
        List<AgendaResponse> agenda = agendaService.gerarAgenda(agendaRequest);
        return ResponseEntity.ok(agenda);
    }


    @PostMapping("/gerarManha")
    public ResponseEntity<List<AgendaResponse>> gerarAgendaManha(@Valid @RequestBody AgendaRequest agendaRequest) {
        List<AgendaResponse> agenda = agendaService.gerarAgendaDaManha(agendaRequest);
        return ResponseEntity.ok(agenda);
    }


    @PostMapping("/gerarTarde")
    public ResponseEntity<List<AgendaResponse>> gerarAgendaTarde(@Valid @RequestBody AgendaRequest agendaRequest) {
        List<AgendaResponse> agenda = agendaService.gerarAgendaDaTarde(agendaRequest);
        return ResponseEntity.ok(agenda);
    }


    @PostMapping("/gerarNoite")
    public ResponseEntity<List<AgendaResponse>> gerarAgendaNoite(@Valid @RequestBody AgendaRequest agendaRequest) {
        List<AgendaResponse> agenda = agendaService.gerarAgendaDaNoite(agendaRequest);
        return ResponseEntity.ok(agenda);
    }


    @PostMapping("/criarAvulso")
    public ResponseEntity<Void> criarAgendaAvulso(@Valid @RequestBody AgendaRequest agendaRequest) {
        agendaService.criarAgendaAvulso(agendaRequest);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/status")
    public ResponseEntity<List<AgendaResponse>> buscarAgendaPorStatus(@RequestParam StatusAgenda status) {
        List<AgendaResponse> agenda = agendaService.buscarAgendaPorStatus(status);
        return ResponseEntity.ok(agenda);
    }


    @PatchMapping("/cancelarAgenda")
    public ResponseEntity<Void> cancelarAgenda(@RequestParam Long agendaId) {
        agendaService.cancelarAgenda(agendaId);
        return ResponseEntity.noContent().build();
    }
}