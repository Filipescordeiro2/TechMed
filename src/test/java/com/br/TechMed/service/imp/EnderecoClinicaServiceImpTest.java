package com.br.TechMed.service.imp;

import com.br.TechMed.dto.Clinica.EnderecoClinicaDTO;
import com.br.TechMed.entity.clinica.EnderecoClinicaEntity;
import com.br.TechMed.repository.clinica.EnderecoClinicaRepository;
import com.br.TechMed.service.imp.clinica.EnderecoClinicaServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EnderecoClinicaServiceImpTest {

    @InjectMocks
    private EnderecoClinicaServiceImp enderecoClinicaService;

    @Mock
    private EnderecoClinicaRepository enderecoClinicaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarEndereco_Sucesso() {
        // Arrange
        EnderecoClinicaDTO enderecoClinicaDTO = new EnderecoClinicaDTO();
        enderecoClinicaDTO.setCep("12345-678");

        EnderecoClinicaEntity enderecoClinicaEntity = new EnderecoClinicaEntity();
        enderecoClinicaEntity.setId(1L);

        when(enderecoClinicaRepository.save(any(EnderecoClinicaEntity.class))).thenReturn(enderecoClinicaEntity);

        // Act
        EnderecoClinicaDTO result = enderecoClinicaService.salvarEndereco(enderecoClinicaDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    public void testSalvarEndereco_ComCepNulo() {
        // Arrange
        EnderecoClinicaDTO enderecoClinicaDTO = new EnderecoClinicaDTO();
        enderecoClinicaDTO.setCep(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            enderecoClinicaService.salvarEndereco(enderecoClinicaDTO);
        });
    }

    @Test
    public void testSalvarEndereco_ComDadosIncompletos() {
        // Arrange
        EnderecoClinicaDTO enderecoClinicaDTO = new EnderecoClinicaDTO();
        // Cep não definido

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            enderecoClinicaService.salvarEndereco(enderecoClinicaDTO);
        });
    }
}