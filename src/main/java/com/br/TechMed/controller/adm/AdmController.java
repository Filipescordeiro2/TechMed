package com.br.TechMed.controller.adm;

import com.br.TechMed.dto.adm.AdminDTO;
import com.br.TechMed.dto.adm.LoginSenhaAdminDTO;
import com.br.TechMed.dto.cliente.ClienteDTO;
import com.br.TechMed.dto.cliente.LoginSenhaClienteDTO;
import com.br.TechMed.service.servicos.adm.AdmService;
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
    private AdmService admService;

    /**
     * Cadastra um novo admin no sistema.
     *
     * @param adminDTO os dados do admin a ser cadastrado
     * @return os dados do admin cadastrado
     */

    @PostMapping("/cadastrarAdmin")
    public ResponseEntity<AdminDTO> cadastrarAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        AdminDTO novoAdmin = admService.cadastrarAdmin(adminDTO);
        return ResponseEntity.ok(novoAdmin);
    }

    /**
     * Autentica um cliente pelo login e senha.
     *
     * @param loginSenhaAdminDTO os dados de login e senha do cliente
     * @return os dados do cliente autenticado
     */
    @PostMapping("/autenticar")
    public ResponseEntity<AdminDTO> autenticarCliente(@RequestBody LoginSenhaAdminDTO loginSenhaAdminDTO) {
        AdminDTO adminAutenticado = admService.autenticarAdmin(loginSenhaAdminDTO);
        return ResponseEntity.ok(adminAutenticado);
    }



}
