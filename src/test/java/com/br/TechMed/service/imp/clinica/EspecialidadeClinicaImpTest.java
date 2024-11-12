package com.br.TechMed.service.imp.clinica;

import com.br.TechMed.Enum.Especialidades;
import com.br.TechMed.dto.Clinica.EspecialidadeClinicaDTO;
import com.br.TechMed.entity.clinica.EspecialidadeClinicaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.clinica.EspecialidadeClinicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para EspecialidadeClinicaImp.
 * Utiliza JUnit 5 e Mockito para testar os métodos de EspecialidadeClinicaImp.
 */
@ExtendWith(MockitoExtension.class)
public class EspecialidadeClinicaImpTest {

    @Mock
    private EspecialidadeClinicaRepository especialidadeClinicaRepository;

    @InjectMocks
    private EspecialidadeClinicaImp especialidadeClinicaService;

    private EspecialidadeClinicaDTO especialidadeClinicaDTO;
    private EspecialidadeClinicaEntity especialidadeClinicaEntity;

    /**
     * Configura os dados de teste antes de cada execução de teste.
     */
    @BeforeEach
    void setUp() {
        especialidadeClinicaDTO = new EspecialidadeClinicaDTO();
        especialidadeClinicaDTO.setEspecialidades(Especialidades.valueOf("CARDIOLOGISTA"));

        especialidadeClinicaEntity = new EspecialidadeClinicaEntity();
        especialidadeClinicaEntity.setEspecialidades(Especialidades.valueOf("CARDIOLOGISTA"));
    }

    /**
     * Testa o método salvarEspecialidade para verificar se ele salva uma especialidade corretamente.
     */
    @Test
    void testSalvarEspecialidade() {
        when(especialidadeClinicaRepository.save(any(EspecialidadeClinicaEntity.class))).thenReturn(especialidadeClinicaEntity);

        EspecialidadeClinicaDTO result = especialidadeClinicaService.salvarEspecialidade(especialidadeClinicaDTO);

        assertNotNull(result);
        assertEquals(especialidadeClinicaDTO.getEspecialidades(), result.getEspecialidades());

        verify(especialidadeClinicaRepository, times(1)).save(any(EspecialidadeClinicaEntity.class));
    }

    /**
     * Testa o método salvarEspecialidade para verificar se ele lança uma exceção corretamente.
     */
    @Test
    void testSalvarEspecialidadeThrowsException() {
        when(especialidadeClinicaRepository.save(any(EspecialidadeClinicaEntity.class))).thenThrow(new RuntimeException("Database error"));

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            especialidadeClinicaService.salvarEspecialidade(especialidadeClinicaDTO);
        });

        assertEquals("Erro ao salvar a especialidade da clínica", exception.getMessage());

        verify(especialidadeClinicaRepository, times(1)).save(any(EspecialidadeClinicaEntity.class));
    }
}