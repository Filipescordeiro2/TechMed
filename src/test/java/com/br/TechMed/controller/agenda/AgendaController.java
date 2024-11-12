package com.br.TechMed.controller.agenda;

import com.br.TechMed.Enum.Jornada;
import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.dto.agenda.AgendaDTO;
import com.br.TechMed.dto.agenda.AgendaDetalhadaDTO;
import com.br.TechMed.service.servicos.agenda.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas à agenda no sistema.
 */
@RestController
@RequestMapping("agendas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Permite apenas o domínio do frontend
public class AgendaController {

    private final AgendaService agendaService;

    /**
     * Gera uma nova agenda.
     *
     * @param agendaDTO Dados da agenda a ser gerada.
     * @return Resposta HTTP 200 (OK) se a operação for bem-sucedida.
     */
    @PostMapping("/gerar")
    public ResponseEntity<Void> gerarAgenda(@Valid @RequestBody AgendaDTO agendaDTO) {
        agendaService.gerarAgenda(agendaDTO.getProfissionalId(), agendaDTO.getData(), agendaDTO.getClinicaId(), agendaDTO.getEspecialidadeId());
        return ResponseEntity.ok().build();
    }

    /**
     * Gera uma nova agenda para o período da manhã.
     *
     * @param agendaDTO Dados da agenda a ser gerada.
     * @return Resposta HTTP 200 (OK) se a operação for bem-sucedida.
     */
    @PostMapping("/gerarManha")
    public ResponseEntity<Void> gerarAgendaManha(@Valid @RequestBody AgendaDTO agendaDTO) {
        agendaService.gerarAgendaDaManha(agendaDTO.getProfissionalId(), agendaDTO.getData(), agendaDTO.getClinicaId(), agendaDTO.getEspecialidadeId());
        return ResponseEntity.ok().build();
    }

    /**
     * Gera uma nova agenda para o período da tarde.
     *
     * @param agendaDTO Dados da agenda a ser gerada.
     * @return Resposta HTTP 200 (OK) se a operação for bem-sucedida.
     */
    @PostMapping("/gerarTarde")
    public ResponseEntity<Void> gerarAgendaTarde(@Valid @RequestBody AgendaDTO agendaDTO) {
        agendaService.gerarAgendaDaTarde(agendaDTO.getProfissionalId(), agendaDTO.getData(), agendaDTO.getClinicaId(), agendaDTO.getEspecialidadeId());
        return ResponseEntity.ok().build();
    }

    /**
     * Gera uma nova agenda para o período da noite.
     *
     * @param agendaDTO Dados da agenda a ser gerada.
     * @return Resposta HTTP 200 (OK) se a operação for bem-sucedida.
     */
    @PostMapping("/gerarNoite")
    public ResponseEntity<Void> gerarAgendaNoite(@Valid @RequestBody AgendaDTO agendaDTO) {
        agendaService.gerarAgendaDaNoite(agendaDTO.getProfissionalId(), agendaDTO.getData(), agendaDTO.getClinicaId(), agendaDTO.getEspecialidadeId());
        return ResponseEntity.ok().build();
    }

    /**
     * Cria uma nova agenda avulsa.
     *
     * @param agendaDTO Dados da agenda a ser criada.
     * @return Resposta HTTP 200 (OK) se a operação for bem-sucedida.
     */
    @PostMapping("/criarAvulso")
    public ResponseEntity<Void> criarAgendaAvulso(@Valid @RequestBody AgendaDTO agendaDTO) {
        agendaService.criarAgendaAvulso(agendaDTO.getProfissionalId(), agendaDTO.getData(), agendaDTO.getHora(), agendaDTO.getClinicaId(), agendaDTO.getEspecialidadeId());
        return ResponseEntity.ok().build();
    }

    /**
     * Busca agendas por profissional.
     *
     * @param profissionalId ID do profissional.
     * @return Lista de agendas do profissional.
     */
    @GetMapping("/profissional")
    public ResponseEntity<List<AgendaDTO>> buscarAgendaPorProfissional(@RequestParam Long profissionalId) {
        List<AgendaDTO> agenda = agendaService.buscarAgendaPorProfissional(profissionalId);
        return ResponseEntity.ok(agenda);
    }
    /**
     * Recupera a agenda de um profissional.
     *
     * @param profissionalId o ID do profissional.
     * @return a lista de agendas do profissional.
     */
    @GetMapping("/agenda")
    public ResponseEntity<List<AgendaDetalhadaDTO>> getAgendaByProfissional(
            @RequestParam(required = false) Long profissionalId,
            @RequestParam Long clinicaId,
            @RequestParam(required = false) String statusAgenda,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) LocalTime hora,
            @RequestParam(required = false) String nomeProfissional,
            @RequestParam(required = false) Jornada periodo) {
        List<AgendaDetalhadaDTO> agenda = agendaService.buscarAgenda(profissionalId, clinicaId, statusAgenda, data, hora, nomeProfissional, periodo);
        return ResponseEntity.ok(agenda);
    }

    /**
     * Busca agendas por status.
     *
     * @param status Status da agenda.
     * @return Lista de agendas com o status especificado.
     */
    @GetMapping("/status")
    public ResponseEntity<List<AgendaDTO>> buscarAgendaPorStatus(@RequestParam StatusAgenda status) {
        List<AgendaDTO> agenda = agendaService.buscarAgendaPorStatus(status);
        return ResponseEntity.ok(agenda);
    }
}