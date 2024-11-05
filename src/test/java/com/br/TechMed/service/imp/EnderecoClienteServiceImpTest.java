package com.br.TechMed.service.imp;

import com.br.TechMed.dto.Clinica.EnderecoClienteDTO;
import com.br.TechMed.entity.cliente.EnderecoClienteEntity;
import com.br.TechMed.repository.cliente.EnderecoClienteRepository;
import com.br.TechMed.service.imp.cliente.EnderecoClienteServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EnderecoClienteServiceImpTest {

    @InjectMocks
    private EnderecoClienteServiceImp enderecoClienteService;

    @Mock
    private EnderecoClienteRepository enderecoClienteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarEndereco_Sucesso() {
        // Arrange
        EnderecoClienteDTO enderecoClienteDTO = new EnderecoClienteDTO();
        enderecoClienteDTO.setCep("12345-678");

        EnderecoClienteEntity enderecoClienteEntity = new EnderecoClienteEntity();
        enderecoClienteEntity.setId(1L);

        when(enderecoClienteRepository.save(any(EnderecoClienteEntity.class))).thenReturn(enderecoClienteEntity);

        // Act
        EnderecoClienteDTO result = enderecoClienteService.salvarEndereco(enderecoClienteDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    public void testSalvarEndereco_ComCepNulo() {
        // Arrange
        EnderecoClienteDTO enderecoClienteDTO = new EnderecoClienteDTO();
        enderecoClienteDTO.setCep(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            enderecoClienteService.salvarEndereco(enderecoClienteDTO);
        });
    }

    @Test
    public void testSalvarEndereco_ComDadosIncompletos() {
        // Arrange
        EnderecoClienteDTO enderecoClienteDTO = new EnderecoClienteDTO();
        // Cep não definido

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            enderecoClienteService.salvarEndereco(enderecoClienteDTO);
        });
    }
}