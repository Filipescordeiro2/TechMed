package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.dto.ClinicaDTO;
import com.br.clinicaSantana.dto.EnderecoClinicaDTO;
import com.br.clinicaSantana.dto.EspecialidadeClinicaDTO;
import com.br.clinicaSantana.entity.ClinicaEntity;
import com.br.clinicaSantana.repository.ClinicaRepository;
import com.br.clinicaSantana.repository.EnderecoClinicaRepository;
import com.br.clinicaSantana.repository.EspecialidadeClinicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClinicaServiceImpTest {

    @InjectMocks
    private ClinicaServiceImp clinicaService;

    @Mock
    private ClinicaRepository clinicaRepository;

    @Mock
    private EnderecoClinicaRepository enderecoClinicaRepository;

    @Mock
    private EspecialidadeClinicaRepository especialidadeClinicaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarClinica_Sucesso() {
        // Arrange
        ClinicaDTO clinicaDTO = new ClinicaDTO();
        clinicaDTO.setNomeClinica("Test Clinic");
        clinicaDTO.setEnderecoClinica(new EnderecoClinicaDTO());
        clinicaDTO.setEspecialidadeClinica(Collections.singletonList(new EspecialidadeClinicaDTO()));

        ClinicaEntity clinicaEntity = new ClinicaEntity();
        clinicaEntity.setId(1L);

        when(clinicaRepository.save(any(ClinicaEntity.class))).thenReturn(clinicaEntity);

        // Act
        ClinicaDTO result = clinicaService.cadastrarClinica(clinicaDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    public void testCadastrarClinica_ComEnderecoNulo() {
        // Arrange
        ClinicaDTO clinicaDTO = new ClinicaDTO();
        clinicaDTO.setNomeClinica("Test Clinic");
        clinicaDTO.setEnderecoClinica(null);
        clinicaDTO.setEspecialidadeClinica(Collections.singletonList(new EspecialidadeClinicaDTO()));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            clinicaService.cadastrarClinica(clinicaDTO);
        });
    }

    @Test
    public void testCadastrarClinica_ComDadosIncompletos() {
        // Arrange
        ClinicaDTO clinicaDTO = new ClinicaDTO();
        clinicaDTO.setNomeClinica("Test Clinic");
        // Endereço e Especialidade não definidos

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            clinicaService.cadastrarClinica(clinicaDTO);
        });
    }
}