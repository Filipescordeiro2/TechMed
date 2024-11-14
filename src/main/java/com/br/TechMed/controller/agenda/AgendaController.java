package com.br.TechMed.controller.agenda;

import com.br.TechMed.Enum.Jornada;
import com.br.TechMed.dto.agenda.AgendaDTO;
import com.br.TechMed.Enum.StatusAgenda;
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
@CrossOrigin(origins = "http://localhost:3000")
public class AgendaController {

    private final AgendaService agendaService;

    /**
     * Gera uma nova agenda.
     *
     * @param agendaDTO Dados da agenda a ser gerada.
     * @return Lista de agendas geradas.
     */
    @PostMapping("/gerar")
    public ResponseEntity<List<AgendaDTO>> gerarAgenda(@Valid @RequestBody AgendaDTO agendaDTO) {
        List<AgendaDTO> agenda = agendaService.gerarAgenda(agendaDTO.getProfissionalId(), agendaDTO.getData(), agendaDTO.getClinicaId(), agendaDTO.getEspecialidadeId());
        return ResponseEntity.ok(agenda);
    }

    /**
     * Gera uma nova agenda para o período da manhã.
     *
     * @param agendaDTO Dados da agenda a ser gerada.
     * @return Lista de agendas geradas.
     */
    @PostMapping("/gerarManha")
    public ResponseEntity<List<AgendaDTO>> gerarAgendaManha(@Valid @RequestBody AgendaDTO agendaDTO) {
        List<AgendaDTO> agenda = agendaService.gerarAgendaDaManha(agendaDTO.getProfissionalId(), agendaDTO.getData(), agendaDTO.getClinicaId(), agendaDTO.getEspecialidadeId());
        return ResponseEntity.ok(agenda);
    }

    /**
     * Gera uma nova agenda para o período da tarde.
     *
     * @param agendaDTO Dados da agenda a ser gerada.
     * @return Lista de agendas geradas.
     */
    @PostMapping("/gerarTarde")
    public ResponseEntity<List<AgendaDTO>> gerarAgendaTarde(@Valid @RequestBody AgendaDTO agendaDTO) {
        List<AgendaDTO> agenda = agendaService.gerarAgendaDaTarde(agendaDTO.getProfissionalId(), agendaDTO.getData(), agendaDTO.getClinicaId(), agendaDTO.getEspecialidadeId());
        return ResponseEntity.ok(agenda);
    }

    /**
     * Gera uma nova agenda para o período da noite.
     *
     * @param agendaDTO Dados da agenda a ser gerada.
     * @return Lista de agendas geradas.
     */
    @PostMapping("/gerarNoite")
    public ResponseEntity<List<AgendaDTO>> gerarAgendaNoite(@Valid @RequestBody AgendaDTO agendaDTO) {
        List<AgendaDTO> agenda = agendaService.gerarAgendaDaNoite(agendaDTO.getProfissionalId(), agendaDTO.getData(), agendaDTO.getClinicaId(), agendaDTO.getEspecialidadeId());
        return ResponseEntity.ok(agenda);
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

    /**
     * Cancela uma agenda pelo ID.
     *
     * @param agendaId o ID da agenda a ser cancelada
     * @return uma resposta vazia com status 204 (No Content)
     */
    @PatchMapping("/cancelarAgenda")
    public ResponseEntity<Void> cancelarAgenda(@RequestParam Long agendaId) {
        agendaService.cancelarAgenda(agendaId);
        return ResponseEntity.noContent().build();
    }
}