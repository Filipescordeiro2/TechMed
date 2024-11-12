package com.br.TechMed.service.imp.profissional;

import com.br.TechMed.Enum.Especialidades;
import com.br.TechMed.dto.profissional.EspecialidadeProfissionalDTO;
import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.profissional.EspecialidadeProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para EspecialidadeProfissionalServiceImp.
 * Utiliza JUnit 5 e Mockito para testar os métodos de EspecialidadeProfissionalServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class EspecialidadeProfissionalServiceImpTest {

    @Mock
    private EspecialidadeProfissionalRepository especialidadeProfissionalRepository;

    @InjectMocks
    private EspecialidadeProfissionalServiceImp especialidadeProfissionalService;

    private EspecialidadeProfissionalDTO especialidadeProfissionalDTO;
    private EspecialidadeProfissionalEntity especialidadeProfissionalEntity;

    @BeforeEach
    void setUp() {
        especialidadeProfissionalDTO = new EspecialidadeProfissionalDTO();
        especialidadeProfissionalDTO.setId(1L);
        especialidadeProfissionalDTO.setEspecialidades(Especialidades.valueOf("CARDIOLOGIA"));

        especialidadeProfissionalEntity = new EspecialidadeProfissionalEntity();
        especialidadeProfissionalEntity.setId(1L);
        especialidadeProfissionalEntity.setEspecialidades(Especialidades.valueOf("CARDIOLOGIA"));
    }

    @Test
    void testSalvarEspecialidade() {
        when(especialidadeProfissionalRepository.save(any(EspecialidadeProfissionalEntity.class))).thenReturn(especialidadeProfissionalEntity);

        EspecialidadeProfissionalDTO result = especialidadeProfissionalService.salvarEspecialidade(especialidadeProfissionalDTO);

        assertNotNull(result);
        assertEquals(especialidadeProfissionalDTO.getId(), result.getId());
        assertEquals(especialidadeProfissionalDTO.getEspecialidades(), result.getEspecialidades());

        verify(especialidadeProfissionalRepository, times(1)).save(any(EspecialidadeProfissionalEntity.class));
    }

    @Test
    void testSalvarEspecialidadeThrowsException() {
        when(especialidadeProfissionalRepository.save(any(EspecialidadeProfissionalEntity.class))).thenThrow(new RuntimeException("Database error"));

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            especialidadeProfissionalService.salvarEspecialidade(especialidadeProfissionalDTO);
        });

        assertEquals("Erro ao salvar a especialidade do profissional", exception.getMessage());

        verify(especialidadeProfissionalRepository, times(1)).save(any(EspecialidadeProfissionalEntity.class));
    }

    @Test
    void testBuscarEspecialidadePorProfissionalId() {
        when(especialidadeProfissionalRepository.findByProfissionalEntityId(1L)).thenReturn(Collections.singletonList(especialidadeProfissionalEntity));

        List<EspecialidadeProfissionalDTO> result = especialidadeProfissionalService.buscarEspecialidadePorProfissionalId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(especialidadeProfissionalDTO.getId(), result.get(0).getId());
        assertEquals(especialidadeProfissionalDTO.getEspecialidades(), result.get(0).getEspecialidades());
    }
}