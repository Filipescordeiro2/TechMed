package com.br.TechMed.service.imp.profissional;

import com.br.TechMed.dto.profissional.EnderecoProfissionalDTO;
import com.br.TechMed.entity.profissional.EnderecoProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.profissional.EnderecoProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para EnderecoProfissionalServiceImp.
 * Utiliza JUnit 5 e Mockito para testar os métodos de EnderecoProfissionalServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class EnderecoProfissionalServiceImpTest {

    @Mock
    private EnderecoProfissionalRepository enderecoProfissionalRepository;

    @InjectMocks
    private EnderecoProfissionalServiceImp enderecoProfissionalService;

    private EnderecoProfissionalDTO enderecoProfissionalDTO;
    private EnderecoProfissionalEntity enderecoProfissionalEntity;

    /**
     * Configura os dados de teste antes de cada execução de teste.
     */
    @BeforeEach
    void setUp() {
        enderecoProfissionalDTO = new EnderecoProfissionalDTO();
        enderecoProfissionalDTO.setCep("12345-678");
        enderecoProfissionalDTO.setLogradouro("Rua Exemplo");
        enderecoProfissionalDTO.setNumero("123");
        enderecoProfissionalDTO.setComplemento("Apto 1");
        enderecoProfissionalDTO.setBairro("Bairro Exemplo");
        enderecoProfissionalDTO.setCidade("Cidade Exemplo");
        enderecoProfissionalDTO.setEstado("Estado Exemplo");
        enderecoProfissionalDTO.setPais("Pais Exemplo");

        enderecoProfissionalEntity = new EnderecoProfissionalEntity();
        enderecoProfissionalEntity.setCep("12345-678");
        enderecoProfissionalEntity.setLogradouro("Rua Exemplo");
        enderecoProfissionalEntity.setNumero("123");
        enderecoProfissionalEntity.setComplemento("Apto 1");
        enderecoProfissionalEntity.setBairro("Bairro Exemplo");
        enderecoProfissionalEntity.setCidade("Cidade Exemplo");
        enderecoProfissionalEntity.setEstado("Estado Exemplo");
        enderecoProfissionalEntity.setPais("Pais Exemplo");
    }

    /**
     * Testa o método salvarEndereco para verificar se ele salva um endereço corretamente.
     */
    @Test
    void testSalvarEndereco() {
        when(enderecoProfissionalRepository.save(any(EnderecoProfissionalEntity.class))).thenReturn(enderecoProfissionalEntity);

        EnderecoProfissionalDTO result = enderecoProfissionalService.salvarEndereco(enderecoProfissionalDTO);

        assertNotNull(result);
        assertEquals(enderecoProfissionalDTO.getCep(), result.getCep());
        assertEquals(enderecoProfissionalDTO.getLogradouro(), result.getLogradouro());
        assertEquals(enderecoProfissionalDTO.getNumero(), result.getNumero());
        assertEquals(enderecoProfissionalDTO.getComplemento(), result.getComplemento());
        assertEquals(enderecoProfissionalDTO.getBairro(), result.getBairro());
        assertEquals(enderecoProfissionalDTO.getCidade(), result.getCidade());
        assertEquals(enderecoProfissionalDTO.getEstado(), result.getEstado());
        assertEquals(enderecoProfissionalDTO.getPais(), result.getPais());

        verify(enderecoProfissionalRepository, times(1)).save(any(EnderecoProfissionalEntity.class));
    }

    /**
     * Testa o método salvarEndereco para verificar se ele lança uma exceção corretamente.
     */
    @Test
    void testSalvarEnderecoThrowsException() {
        when(enderecoProfissionalRepository.save(any(EnderecoProfissionalEntity.class))).thenThrow(new RuntimeException("Database error"));

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            enderecoProfissionalService.salvarEndereco(enderecoProfissionalDTO);
        });

        assertEquals("Erro ao salvar o endereço do cliente", exception.getMessage());

        verify(enderecoProfissionalRepository, times(1)).save(any(EnderecoProfissionalEntity.class));
    }
}