package com.br.TechMed.service.imp.cliente;

import com.br.TechMed.entity.cliente.EnderecoClienteEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.cliente.EnderecoClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para EnderecoClienteServiceImp.
 * Utiliza JUnit 5 e Mockito para testar os métodos de EnderecoClienteServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class EnderecoClienteServiceTest {

    @Mock
    private EnderecoClienteRepository enderecoClienteRepository;

    @InjectMocks
    private EnderecoClienteServiceImp enderecoClienteService;

    private EnderecoClienteDTO enderecoClienteDTO;
    private EnderecoClienteEntity enderecoClienteEntity;

    /**
     * Configura os dados de teste antes de cada execução de teste.
     */
    @BeforeEach
    void setUp() {
        enderecoClienteDTO = new EnderecoClienteDTO();
        enderecoClienteDTO.setCep("12345-678");
        enderecoClienteDTO.setLogradouro("Rua Exemplo");
        enderecoClienteDTO.setNumero("123");
        enderecoClienteDTO.setComplemento("Apto 1");
        enderecoClienteDTO.setBairro("Bairro Exemplo");
        enderecoClienteDTO.setCidade("Cidade Exemplo");
        enderecoClienteDTO.setEstado("Estado Exemplo");
        enderecoClienteDTO.setPais("Pais Exemplo");

        enderecoClienteEntity = new EnderecoClienteEntity();
        enderecoClienteEntity.setCep("12345-678");
        enderecoClienteEntity.setLogradouro("Rua Exemplo");
        enderecoClienteEntity.setNumero("123");
        enderecoClienteEntity.setComplemento("Apto 1");
        enderecoClienteEntity.setBairro("Bairro Exemplo");
        enderecoClienteEntity.setCidade("Cidade Exemplo");
        enderecoClienteEntity.setEstado("Estado Exemplo");
        enderecoClienteEntity.setPais("Pais Exemplo");
    }

    /**
     * Testa o método salvarEndereco para verificar se ele salva um endereço corretamente.
     */
    @Test
    void testSalvarEndereco() {
        when(enderecoClienteRepository.save(any(EnderecoClienteEntity.class))).thenReturn(enderecoClienteEntity);

        EnderecoClienteDTO result = enderecoClienteService.salvarEndereco(enderecoClienteDTO);

        assertNotNull(result);
        assertEquals(enderecoClienteDTO.getCep(), result.getCep());
        assertEquals(enderecoClienteDTO.getLogradouro(), result.getLogradouro());
        assertEquals(enderecoClienteDTO.getNumero(), result.getNumero());
        assertEquals(enderecoClienteDTO.getComplemento(), result.getComplemento());
        assertEquals(enderecoClienteDTO.getBairro(), result.getBairro());
        assertEquals(enderecoClienteDTO.getCidade(), result.getCidade());
        assertEquals(enderecoClienteDTO.getEstado(), result.getEstado());
        assertEquals(enderecoClienteDTO.getPais(), result.getPais());

        verify(enderecoClienteRepository, times(1)).save(any(EnderecoClienteEntity.class));
    }

    /**
     * Testa o método salvarEndereco para verificar se ele lança uma exceção corretamente.
     */
    @Test
    void testSalvarEnderecoThrowsException() {
        when(enderecoClienteRepository.save(any(EnderecoClienteEntity.class))).thenThrow(new RuntimeException("Database error"));

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            enderecoClienteService.salvarEndereco(enderecoClienteDTO);
        });

        assertEquals("Erro ao salvar o endereço do cliente", exception.getMessage());

        verify(enderecoClienteRepository, times(1)).save(any(EnderecoClienteEntity.class));
    }
}