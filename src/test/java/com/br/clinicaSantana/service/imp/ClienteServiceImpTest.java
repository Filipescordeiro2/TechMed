package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.dto.ClienteDTO;
import com.br.clinicaSantana.dto.EnderecoClienteDTO;
import com.br.clinicaSantana.entity.ClienteEntity;
import com.br.clinicaSantana.entity.EnderecoClienteEntity;
import com.br.clinicaSantana.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClienteServiceImpTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImp clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarCliente_Sucesso() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setLogin("testLogin");
        clienteDTO.setSenha("testSenha");
        clienteDTO.setNome("testNome");
        clienteDTO.setSobrenome("testSobrenome");
        clienteDTO.setEmail("testEmail");
        clienteDTO.setCpf("testCpf");
        clienteDTO.setCelular("testCelular");

        EnderecoClienteDTO enderecoDTO = new EnderecoClienteDTO();
        enderecoDTO.setCep("12345-678");
        enderecoDTO.setLogradouro("Rua Teste");
        enderecoDTO.setNumero("123");
        enderecoDTO.setComplemento("Apto 1");
        enderecoDTO.setBairro("Bairro Teste");
        enderecoDTO.setCidade("Cidade Teste");
        enderecoDTO.setEstado("Estado Teste");
        enderecoDTO.setPais("Pais Teste");
        clienteDTO.setEnderecoCliente(enderecoDTO);

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(1L);
        clienteEntity.setLogin("testLogin");
        clienteEntity.setSenha("testSenha");
        clienteEntity.setNome("testNome");
        clienteEntity.setSobrenome("testSobrenome");
        clienteEntity.setEmail("testEmail");
        clienteEntity.setCpf("testCpf");
        clienteEntity.setCelular("testCelular");

        EnderecoClienteEntity enderecoEntity = new EnderecoClienteEntity();
        enderecoEntity.setCep("12345-678");
        enderecoEntity.setLogradouro("Rua Teste");
        enderecoEntity.setNumero("123");
        enderecoEntity.setComplemento("Apto 1");
        enderecoEntity.setBairro("Bairro Teste");
        enderecoEntity.setCidade("Cidade Teste");
        enderecoEntity.setEstado("Estado Teste");
        enderecoEntity.setPais("Pais Teste");
        clienteEntity.getEnderecos().add(enderecoEntity);

        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        // Act
        ClienteDTO result = clienteService.cadastrarCliente(clienteDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    public void testCadastrarCliente_ComEnderecoNulo() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setLogin("testLogin");
        clienteDTO.setSenha("testSenha");
        clienteDTO.setNome("testNome");
        clienteDTO.setSobrenome("testSobrenome");
        clienteDTO.setEmail("testEmail");
        clienteDTO.setCpf("testCpf");
        clienteDTO.setCelular("testCelular");
        clienteDTO.setEnderecoCliente(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            clienteService.cadastrarCliente(clienteDTO);
        });
    }

    @Test
    public void testCadastrarCliente_ComDadosIncompletos() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setLogin("testLogin");
        clienteDTO.setSenha("testSenha");
        // Nome e Sobrenome não definidos
        clienteDTO.setEmail("testEmail");
        clienteDTO.setCpf("testCpf");
        clienteDTO.setCelular("testCelular");

        EnderecoClienteDTO enderecoDTO = new EnderecoClienteDTO();
        enderecoDTO.setCep("12345-678");
        enderecoDTO.setLogradouro("Rua Teste");
        enderecoDTO.setNumero("123");
        enderecoDTO.setComplemento("Apto 1");
        enderecoDTO.setBairro("Bairro Teste");
        enderecoDTO.setCidade("Cidade Teste");
        enderecoDTO.setEstado("Estado Teste");
        enderecoDTO.setPais("Pais Teste");
        clienteDTO.setEnderecoCliente(enderecoDTO);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            clienteService.cadastrarCliente(clienteDTO);
        });
    }
}