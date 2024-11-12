package com.br.TechMed.service.imp.cliente;

import com.br.TechMed.dto.Clinica.EnderecoClienteDTO;
import com.br.TechMed.dto.cliente.ClienteDTO;
import com.br.TechMed.dto.cliente.LoginSenhaClienteDTO;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.cliente.EnderecoClienteEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.cliente.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para ClienteServiceImp.
 * Utiliza JUnit 5 e Mockito para testar os métodos de ClienteServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class ClienteServiceImpTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImp clienteService;

    private ClienteDTO clienteDTO;
    private ClienteEntity clienteEntity;
    private EnderecoClienteEntity enderecoEntity;

    /**
     * Configura os dados de teste antes de cada execução de teste.
     */
    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setLogin("cliente");
        clienteDTO.setSenha("password");
        clienteDTO.setNome("Cliente");
        clienteDTO.setSobrenome("User");
        clienteDTO.setEmail("cliente@example.com");
        clienteDTO.setCpf("12345678900");
        clienteDTO.setCelular("123456789");

        EnderecoClienteDTO enderecoDTO = new EnderecoClienteDTO();
        enderecoDTO.setCep("12345-678");
        enderecoDTO.setLogradouro("Rua Exemplo");
        enderecoDTO.setNumero("123");
        enderecoDTO.setComplemento("Apto 1");
        enderecoDTO.setBairro("Bairro Exemplo");
        enderecoDTO.setCidade("Cidade Exemplo");
        enderecoDTO.setEstado("Estado Exemplo");
        enderecoDTO.setPais("Pais Exemplo");
        clienteDTO.setEnderecoCliente(enderecoDTO);

        clienteEntity = new ClienteEntity();
        clienteEntity.setLogin("cliente");
        clienteEntity.setSenha("password");
        clienteEntity.setNome("Cliente");
        clienteEntity.setSobrenome("User");
        clienteEntity.setEmail("cliente@example.com");
        clienteEntity.setCpf("12345678900");
        clienteEntity.setCelular("123456789");

        enderecoEntity = new EnderecoClienteEntity();
        enderecoEntity.setCep("12345-678");
        enderecoEntity.setLogradouro("Rua Exemplo");
        enderecoEntity.setNumero("123");
        enderecoEntity.setComplemento("Apto 1");
        enderecoEntity.setBairro("Bairro Exemplo");
        enderecoEntity.setCidade("Cidade Exemplo");
        enderecoEntity.setEstado("Estado Exemplo");
        enderecoEntity.setPais("Pais Exemplo");
        enderecoEntity.setClienteEntity(clienteEntity);
        clienteEntity.getEnderecos().add(enderecoEntity);
    }

    /**
     * Testa o método cadastrarCliente para verificar se ele cadastra um cliente corretamente.
     */
    @Test
    void testCadastrarCliente() {
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        ClienteDTO result = clienteService.cadastrarCliente(clienteDTO);

        assertNotNull(result);
        assertEquals(clienteDTO.getLogin(), result.getLogin());
        assertEquals(clienteDTO.getSenha(), result.getSenha());
        assertEquals(clienteDTO.getNome(), result.getNome());
        assertEquals(clienteDTO.getSobrenome(), result.getSobrenome());
        assertEquals(clienteDTO.getEmail(), result.getEmail());
        assertEquals(clienteDTO.getCpf(), result.getCpf());
        assertEquals(clienteDTO.getCelular(), result.getCelular());

        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

    /**
     * Testa o método buscarClientePorCpf para verificar se ele busca um cliente corretamente.
     */
    @Test
    void testBuscarClientePorCpf() {
        when(clienteRepository.findByCpf("12345678900")).thenReturn(Optional.of(clienteEntity));

        ClienteDTO result = clienteService.buscarClientePorCpf("12345678900");

        assertNotNull(result);
        assertEquals(clienteDTO.getCpf(), result.getCpf());
    }

    /**
     * Testa o método autenticarCliente para verificar se ele autentica um cliente corretamente.
     */
    @Test
    void testAutenticarCliente() {
        LoginSenhaClienteDTO loginSenhaClienteDTO = new LoginSenhaClienteDTO();
        loginSenhaClienteDTO.setLogin("cliente");
        loginSenhaClienteDTO.setSenha("password");

        when(clienteRepository.findByLogin("cliente")).thenReturn(Optional.of(clienteEntity));

        ClienteDTO result = clienteService.autenticarCliente(loginSenhaClienteDTO);

        assertNotNull(result);
        assertEquals(clienteDTO.getLogin(), result.getLogin());
    }

    /**
     * Testa o método autenticarCliente para verificar se ele lança uma exceção quando o login ou senha são inválidos.
     */
    @Test
    void testAutenticarClienteThrowsException() {
        LoginSenhaClienteDTO loginSenhaClienteDTO = new LoginSenhaClienteDTO();
        loginSenhaClienteDTO.setLogin("cliente");
        loginSenhaClienteDTO.setSenha("wrongpassword");

        when(clienteRepository.findByLogin("cliente")).thenReturn(Optional.of(clienteEntity));

        assertThrows(RegraDeNegocioException.class, () -> {
            clienteService.autenticarCliente(loginSenhaClienteDTO);
        });
    }
}