package com.br.TechMed.service.imp;

import com.br.TechMed.dto.Clinica.ClinicaDTO;
import com.br.TechMed.dto.Clinica.EnderecoClinicaDTO;
import com.br.TechMed.dto.Clinica.EspecialidadeClinicaDTO;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.clinica.EnderecoClinicaRepository;
import com.br.TechMed.repository.clinica.EspecialidadeClinicaRepository;
import com.br.TechMed.service.imp.clinica.ClinicaServiceImp;
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