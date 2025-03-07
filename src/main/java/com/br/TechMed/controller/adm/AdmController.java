package com.br.TechMed.controller.adm;

import com.br.TechMed.dto.request.Adm.AdminRequest;
import com.br.TechMed.dto.request.Adm.LoginSenhaAdminRequest;
import com.br.TechMed.dto.response.adm.AdminRegisterResponse;
import com.br.TechMed.dto.response.adm.AdminResponse;
import com.br.TechMed.service.adm.AdmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Permite apenas o domínio do frontend
@RequestMapping("/adm")
public class AdmController {

    @Autowired
    private final AdmService admService;

    @PostMapping("/cadastrarAdmin")
    public ResponseEntity<AdminRegisterResponse> cadastrarAdmin(@Valid @RequestBody AdminRequest request) {
        var novoAdmin = admService.cadastrarAdmin(request);
        return ResponseEntity.ok(novoAdmin);
    }

    @PostMapping("/autenticar")
    public ResponseEntity<AdminResponse> autenticarCliente(@RequestBody LoginSenhaAdminRequest request) {
        var adminAutenticado = admService.autenticarAdmin(request);
        return ResponseEntity.ok(adminAutenticado);
    }



}
