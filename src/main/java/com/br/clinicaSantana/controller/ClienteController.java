package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.ClienteDTO;
import com.br.clinicaSantana.dto.LoginSenhaClienteDTO;
import com.br.clinicaSantana.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Permite apenas o domínio do frontend
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Cadastra um novo cliente no sistema.
     *
     * @param clienteDTO os dados do cliente a ser cadastrado
     * @return os dados do cliente cadastrado
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteDTO> cadastrarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO novoCliente = clienteService.cadastrarCliente(clienteDTO);
        return ResponseEntity.ok(novoCliente);
    }

    /**
     * Busca um cliente pelo CPF.
     *
     * @param cpf o CPF do cliente a ser buscado
     * @return os dados do cliente encontrado
     */
    @GetMapping("/buscarPorCpf")
    public ResponseEntity<ClienteDTO> buscarClientePorCpf(@RequestParam String cpf) {
        ClienteDTO cliente = clienteService.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Autentica um cliente pelo login e senha.
     *
     * @param loginSenhaClienteDTO os dados de login e senha do cliente
     * @return os dados do cliente autenticado
     */
    @PostMapping("/autenticar")
    public ResponseEntity<ClienteDTO> autenticarCliente(@RequestBody LoginSenhaClienteDTO loginSenhaClienteDTO) {
        ClienteDTO clienteAutenticado = clienteService.autenticarCliente(loginSenhaClienteDTO);
        return ResponseEntity.ok(clienteAutenticado);
    }
}