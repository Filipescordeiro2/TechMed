package com.br.TechMed.service.imp.clinica;

import com.br.TechMed.dto.Clinica.ProfissionaisClinicaDTO;
import com.br.TechMed.dto.Clinica.ProfissionaisClinicaDetalhadoDTO;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.clinica.ProfissionaisClinicaEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.clinica.ProfissionaisClinicaRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
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
 * Classe de teste para ProfissionaisClinicaServiceImp.
 * Utiliza JUnit 5 e Mockito para testar os métodos de ProfissionaisClinicaServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class ProfissionaisClinicaServiceImpTest {

    @Mock
    private ProfissionaisClinicaRepository profissionaisClinicaRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ClinicaRepository clinicaRepository;

    @InjectMocks
    private ProfissionaisClinicaServiceImp profissionaisClinicaService;

    private ProfissionaisClinicaDTO profissionaisClinicaDTO;
    private ProfissionaisClinicaEntity profissionaisClinicaEntity;
    private ClinicaEntity clinicaEntity;
    private ProfissionalEntity profissionalEntity;

    @BeforeEach
    void setUp() {
        profissionaisClinicaDTO = new ProfissionaisClinicaDTO();
        profissionaisClinicaDTO.setId(1L);
        profissionaisClinicaDTO.setClinicaId(1L);
        profissionaisClinicaDTO.setProfissionalId(1L);

        clinicaEntity = new ClinicaEntity();
        clinicaEntity.setId(1L);
        clinicaEntity.setNomeClinica("Clinica Teste");

        profissionalEntity = new ProfissionalEntity();
        profissionalEntity.setId(1L);
        profissionalEntity.setNome("Profissional Teste");

        profissionaisClinicaEntity = new ProfissionaisClinicaEntity();
        profissionaisClinicaEntity.setId(1L);
        profissionaisClinicaEntity.setClinicaEntity(clinicaEntity);
        profissionaisClinicaEntity.setProfissional(profissionalEntity);
    }

    @Test
    void testSave() {
        when(profissionaisClinicaRepository.existsByClinicaEntityIdAndProfissionalId(1L, 1L)).thenReturn(false);
        when(profissionaisClinicaRepository.save(any(ProfissionaisClinicaEntity.class))).thenReturn(profissionaisClinicaEntity);

        ProfissionaisClinicaDTO result = profissionaisClinicaService.save(profissionaisClinicaDTO);

        assertNotNull(result);
        assertEquals(profissionaisClinicaDTO.getClinicaId(), result.getClinicaId());
        assertEquals(profissionaisClinicaDTO.getProfissionalId(), result.getProfissionalId());

        verify(profissionaisClinicaRepository, times(1)).save(any(ProfissionaisClinicaEntity.class));
    }

    @Test
    void testSaveThrowsExceptionWhenExists() {
        when(profissionaisClinicaRepository.existsByClinicaEntityIdAndProfissionalId(1L, 1L)).thenReturn(true);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            profissionaisClinicaService.save(profissionaisClinicaDTO);
        });

        assertEquals("Já existe um profissional com este ID para a clínica especificada.", exception.getMessage());
    }

    @Test
    void testFindByProfissionalId() {
        when(profissionaisClinicaRepository.findByProfissionalId(1L)).thenReturn(Collections.singletonList(profissionaisClinicaEntity));

        List<ProfissionaisClinicaDTO> result = profissionaisClinicaService.findByProfissionalId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profissionaisClinicaDTO.getProfissionalId(), result.get(0).getProfissionalId());
    }

    @Test
    void testFindByClinicaId() {
        when(profissionaisClinicaRepository.findByClinicaEntityId(1L)).thenReturn(Collections.singletonList(profissionaisClinicaEntity));

        List<ProfissionaisClinicaDTO> result = profissionaisClinicaService.findByClinicaId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profissionaisClinicaDTO.getClinicaId(), result.get(0).getClinicaId());
    }

    @Test
    void testFindByProfissionalIdDetalhada() {
        when(profissionalRepository.existsById(1L)).thenReturn(true);
        when(profissionaisClinicaRepository.findByProfissionalId(1L)).thenReturn(Collections.singletonList(profissionaisClinicaEntity));

        List<ProfissionaisClinicaDetalhadoDTO> result = profissionaisClinicaService.findByProfissionalIdDetalhada(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profissionaisClinicaEntity.getId(), result.get(0).getId());
        assertEquals(clinicaEntity.getId(), result.get(0).getClinicaId());
        assertEquals(clinicaEntity.getNomeClinica(), result.get(0).getNomeclinica());
        assertEquals(profissionalEntity.getId(), result.get(0).getProfissionalId());
        assertEquals(profissionalEntity.getNome(), result.get(0).getNomeProfissional());
    }

    @Test
    void testFindByProfissionalIdDetalhadaThrowsExceptionWhenNotFound() {
        when(profissionalRepository.existsById(1L)).thenReturn(false);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            profissionaisClinicaService.findByProfissionalIdDetalhada(1L);
        });

        assertEquals("Profissional não encontrado", exception.getMessage());
    }

    @Test
    void testFindByClinicaIdDetalhada() {
        when(clinicaRepository.existsById(1L)).thenReturn(true);
        when(profissionaisClinicaRepository.findByClinicaEntityId(1L)).thenReturn(Collections.singletonList(profissionaisClinicaEntity));

        List<ProfissionaisClinicaDetalhadoDTO> result = profissionaisClinicaService.findByClinicaIdDetalhada(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(profissionaisClinicaEntity.getId(), result.get(0).getId());
        assertEquals(clinicaEntity.getId(), result.get(0).getClinicaId());
        assertEquals(clinicaEntity.getNomeClinica(), result.get(0).getNomeclinica());
        assertEquals(profissionalEntity.getId(), result.get(0).getProfissionalId());
        assertEquals(profissionalEntity.getNome(), result.get(0).getNomeProfissional());
    }

    @Test
    void testFindByClinicaIdDetalhadaThrowsExceptionWhenNotFound() {
        when(clinicaRepository.existsById(1L)).thenReturn(false);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            profissionaisClinicaService.findByClinicaIdDetalhada(1L);
        });

        assertEquals("Clinica não encontrado", exception.getMessage());
    }
}