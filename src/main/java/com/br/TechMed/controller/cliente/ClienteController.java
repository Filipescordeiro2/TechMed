package com.br.TechMed.controller.cliente;

import com.br.TechMed.dto.request.cliente.ClienteRequest;
import com.br.TechMed.dto.request.cliente.LoginSenhaClienteResquest;
import com.br.TechMed.dto.response.cliente.ClienteRegisterResponse;
import com.br.TechMed.dto.response.cliente.ClienteResponse;
import com.br.TechMed.service.imp.cliente.ClienteService;
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

    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteRegisterResponse> cadastrarCliente(@Valid @RequestBody ClienteRequest request) {
        var novoCliente = clienteService.cadastrarCliente(request);
        return ResponseEntity.ok(novoCliente);
    }


    @GetMapping("/buscarPorCpf")
    public ResponseEntity<ClienteResponse> buscarClientePorCpf(@RequestParam String cpf) {
        var cliente = clienteService.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }


    @PostMapping("/autenticar")
    public ResponseEntity<ClienteResponse> autenticarCliente(@RequestBody LoginSenhaClienteResquest resquest) {
        var clienteAutenticado = clienteService.autenticarCliente(resquest);
        return ResponseEntity.ok(clienteAutenticado);
    }


    @GetMapping("/contar")
    public ResponseEntity<Long> contarClientes() {
        long quantidadeClientes = clienteService.contarClientes();
        return ResponseEntity.ok(quantidadeClientes);
    }

}