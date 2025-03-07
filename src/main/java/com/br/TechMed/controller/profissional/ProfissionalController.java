package com.br.TechMed.controller.profissional;

import com.br.TechMed.dto.request.Profissional.LoginSenhaProfissionalRequest;
import com.br.TechMed.dto.request.Profissional.ProfissionalRequest;
import com.br.TechMed.dto.response.Profissional.AgendaDetalhadaResponse;
import com.br.TechMed.dto.response.Profissional.EspecialidadeProfissionalResponse;
import com.br.TechMed.dto.response.Profissional.ProfissionalRegisterResponse;
import com.br.TechMed.dto.response.Profissional.ProfissionalResponse;
import com.br.TechMed.service.profissional.EspecialidadeProfissionalService;
import com.br.TechMed.service.profissional.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/profissionais")
@CrossOrigin(origins = "http://localhost:3000") // Permite apenas o domínio do frontend
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private EspecialidadeProfissionalService especialidadeProfissionalService;

    @PostMapping
    public ResponseEntity<ProfissionalRegisterResponse> createProfissional(@RequestBody ProfissionalRequest request) {
        var createdProfissional = profissionalService.cadastrarProfissional(request);
        return ResponseEntity.ok(createdProfissional);
    }

    @PostMapping("/autenticar")
    public ResponseEntity<ProfissionalResponse> autenticarProfissional(@RequestBody LoginSenhaProfissionalRequest loginSenhaDTO) {
        var profissionalAutenticado = profissionalService.autenticarProfissional(loginSenhaDTO);
        return ResponseEntity.ok(profissionalAutenticado);
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<AgendaDetalhadaResponse>> getAgendaByProfissional(@RequestParam(required = false) Long profissionalId,
                                                                                 @RequestParam Long clinicaId,
                                                                                 @RequestParam(required = false) String statusAgenda,
                                                                                 @RequestParam(required = false) LocalDate data,
                                                                                 @RequestParam(required = false) LocalTime hora,
                                                                                 @RequestParam(required = false) String nomeProfissional,
                                                                                 @RequestParam(required = false) String nomeEspecialidade) {

        List<AgendaDetalhadaResponse> agenda = profissionalService.getAgendaByProfissional(profissionalId, clinicaId,
                statusAgenda, data, hora, nomeProfissional, nomeEspecialidade);
        return ResponseEntity.ok(agenda);
    }


    @GetMapping("/contar")
    public ResponseEntity<Long> contarProfissionais() {
        long quantidadeProfissionais = profissionalService.contarProfissionais();
        return ResponseEntity.ok(quantidadeProfissionais);
    }

    @PatchMapping("/inativarProfissional/{id}")
    public void updateStatus(@PathVariable("id") Long id) {
        profissionalService.atualizarStatusProfissional(id);
    }

    @GetMapping("/especialidadesProfissional")
    public ResponseEntity<List<EspecialidadeProfissionalResponse>> buscarEspecialidadePorProfissionalId(@RequestParam Long profissionalId) {
        List<EspecialidadeProfissionalResponse> especialidades = especialidadeProfissionalService.buscarEspecialidadePorProfissionalId(profissionalId);
        return ResponseEntity.ok(especialidades);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ProfissionalResponse>> listarProfissionaisAtivos() {
        List<ProfissionalResponse> profissionaisAtivos = profissionalService.listarProfissionaisAtivos();
        return ResponseEntity.ok(profissionaisAtivos);
    }

    @GetMapping("/verificar-cpf/{cpf}")
    public ResponseEntity<Map<String, Boolean>> verificarCpfDuplicado(@PathVariable String cpf) {
        boolean duplicado = profissionalService.isCpfDuplicado(cpf);
        Map<String, Boolean> response = new HashMap<>();
        response.put("duplicado", duplicado);
        return ResponseEntity.ok(response);
    }

}