package com.br.TechMed.service.imp.clinica;

import com.br.TechMed.dto.Clinica.EnderecoClinicaDTO;
import com.br.TechMed.entity.clinica.EnderecoClinicaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.clinica.EnderecoClinicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para EnderecoClinicaServiceImp.
 * Utiliza JUnit 5 e Mockito para testar os métodos de EnderecoClinicaServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class EnderecoClinicaServiceImpTest {

    @Mock
    private EnderecoClinicaRepository enderecoClinicaRepository;

    @InjectMocks
    private EnderecoClinicaServiceImp enderecoClinicaService;

    private EnderecoClinicaDTO enderecoClinicaDTO;
    private EnderecoClinicaEntity enderecoClinicaEntity;

    /**
     * Configura os dados de teste antes de cada execução de teste.
     */
    @BeforeEach
    void setUp() {
        enderecoClinicaDTO = new EnderecoClinicaDTO();
        enderecoClinicaDTO.setCep("12345-678");
        enderecoClinicaDTO.setLogradouro("Rua Exemplo");
        enderecoClinicaDTO.setNumero("123");
        enderecoClinicaDTO.setComplemento("Apto 1");
        enderecoClinicaDTO.setBairro("Bairro Exemplo");
        enderecoClinicaDTO.setCidade("Cidade Exemplo");
        enderecoClinicaDTO.setEstado("Estado Exemplo");
        enderecoClinicaDTO.setPais("Pais Exemplo");

        enderecoClinicaEntity = new EnderecoClinicaEntity();
        enderecoClinicaEntity.setCep("12345-678");
        enderecoClinicaEntity.setLogradouro("Rua Exemplo");
        enderecoClinicaEntity.setNumero("123");
        enderecoClinicaEntity.setComplemento("Apto 1");
        enderecoClinicaEntity.setBairro("Bairro Exemplo");
        enderecoClinicaEntity.setCidade("Cidade Exemplo");
        enderecoClinicaEntity.setEstado("Estado Exemplo");
        enderecoClinicaEntity.setPais("Pais Exemplo");
    }

    /**
     * Testa o método salvarEndereco para verificar se ele salva um endereço corretamente.
     */
    @Test
    void testSalvarEndereco() {
        when(enderecoClinicaRepository.save(any(EnderecoClinicaEntity.class))).thenReturn(enderecoClinicaEntity);

        EnderecoClinicaDTO result = enderecoClinicaService.salvarEndereco(enderecoClinicaDTO);

        assertNotNull(result);
        assertEquals(enderecoClinicaDTO.getCep(), result.getCep());
        assertEquals(enderecoClinicaDTO.getLogradouro(), result.getLogradouro());
        assertEquals(enderecoClinicaDTO.getNumero(), result.getNumero());
        assertEquals(enderecoClinicaDTO.getComplemento(), result.getComplemento());
        assertEquals(enderecoClinicaDTO.getBairro(), result.getBairro());
        assertEquals(enderecoClinicaDTO.getCidade(), result.getCidade());
        assertEquals(enderecoClinicaDTO.getEstado(), result.getEstado());
        assertEquals(enderecoClinicaDTO.getPais(), result.getPais());

        verify(enderecoClinicaRepository, times(1)).save(any(EnderecoClinicaEntity.class));
    }

    /**
     * Testa o método salvarEndereco para verificar se ele lança uma exceção corretamente.
     */
    @Test
    void testSalvarEnderecoThrowsException() {
        when(enderecoClinicaRepository.save(any(EnderecoClinicaEntity.class))).thenThrow(new RuntimeException("Database error"));

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            enderecoClinicaService.salvarEndereco(enderecoClinicaDTO);
        });

        assertEquals("Erro ao salvar o endereço da clínica", exception.getMessage());

        verify(enderecoClinicaRepository, times(1)).save(any(EnderecoClinicaEntity.class));
    }
}