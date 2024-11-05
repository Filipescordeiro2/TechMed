package com.br.TechMed.service.imp;

import com.br.TechMed.Enum.Especialidades;
import com.br.TechMed.dto.Clinica.EspecialidadeClinicaDTO;
import com.br.TechMed.entity.clinica.EspecialidadeClinicaEntity;
import com.br.TechMed.repository.clinica.EspecialidadeClinicaRepository;
import com.br.TechMed.service.imp.clinica.EspecialidadeClinicaImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EspecialidadeClinicaImpTest {

    @InjectMocks
    private EspecialidadeClinicaImp especialidadeClinicaService;

    @Mock
    private EspecialidadeClinicaRepository especialidadeClinicaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarEspecialidade_Sucesso() {
        // Arrange
        EspecialidadeClinicaDTO especialidadeClinicaDTO = new EspecialidadeClinicaDTO();
        especialidadeClinicaDTO.setEspecialidades(Especialidades.valueOf("CARDIOLOGISTA"));

        EspecialidadeClinicaEntity especialidadeClinicaEntity = new EspecialidadeClinicaEntity();
        especialidadeClinicaEntity.setId(1L);

        when(especialidadeClinicaRepository.save(any(EspecialidadeClinicaEntity.class))).thenReturn(especialidadeClinicaEntity);

        // Act
        EspecialidadeClinicaDTO result = especialidadeClinicaService.salvarEspecialidade(especialidadeClinicaDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    public void testSalvarEspecialidade_ComNomeNulo() {
        // Arrange
        EspecialidadeClinicaDTO especialidadeClinicaDTO = new EspecialidadeClinicaDTO();
        especialidadeClinicaDTO.setEspecialidades(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            especialidadeClinicaService.salvarEspecialidade(especialidadeClinicaDTO);
        });
    }

    @Test
    public void testSalvarEspecialidade_ComDadosIncompletos() {
        // Arrange
        EspecialidadeClinicaDTO especialidadeClinicaDTO = new EspecialidadeClinicaDTO();
        // NomeEspecialidade não definido

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            especialidadeClinicaService.salvarEspecialidade(especialidadeClinicaDTO);
        });
    }
}