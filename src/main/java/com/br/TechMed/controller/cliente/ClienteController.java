package com.br.TechMed.controller.cliente;

import com.br.TechMed.dto.cliente.ClienteDTO;
import com.br.TechMed.dto.cliente.LoginSenhaClienteDTO;
import com.br.TechMed.service.servicos.cliente.ClienteService;
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

    /**
     * Retorna a quantidade de clientes cadastrados.
     *
     * @return a quantidade de clientes cadastrados
     */
    @GetMapping("/contar")
    public ResponseEntity<Long> contarClientes() {
        long quantidadeClientes = clienteService.contarClientes();
        return ResponseEntity.ok(quantidadeClientes);
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteAtualizado = clienteService.atualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }
}