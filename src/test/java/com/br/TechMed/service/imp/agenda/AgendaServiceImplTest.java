package com.br.TechMed.service.imp.agenda;

import com.br.TechMed.Enum.Jornada;
import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.dto.agenda.AgendaDTO;
import com.br.TechMed.entity.agenda.AgendaEntity;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.agenda.AgendaRepository;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.repository.clinica.ProfissionaisClinicaRepository;
import com.br.TechMed.repository.profissional.EspecialidadeProfissionalRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para AgendaServiceImpl.
 * Utiliza JUnit 5 e Mockito para testar os métodos de AgendaServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
public class AgendaServiceImplTest {

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ClinicaRepository clinicaRepository;

    @Mock
    private EspecialidadeProfissionalRepository especialidadeProfissionalRepository;

    @Mock
    private ProfissionaisClinicaRepository profissionaisClinicaRepository;

    @InjectMocks
    private AgendaServiceImpl agendaService;

    private ProfissionalEntity profissional;
    private ClinicaEntity clinica;
    private EspecialidadeProfissionalEntity especialidade;
    private AgendaDTO agendaDTO;
    private AgendaEntity agendaEntity;

    /**
     * Configura os dados de teste antes de cada execução de teste.
     */
    @BeforeEach
    void setUp() {
        profissional = new ProfissionalEntity();
        profissional.setId(1L);

        clinica = new ClinicaEntity();
        clinica.setId(1L);

        especialidade = new EspecialidadeProfissionalEntity();
        especialidade.setId(1L);

        agendaDTO = new AgendaDTO();
        agendaDTO.setProfissionalId(1L);
        agendaDTO.setClinicaId(1L);
        agendaDTO.setEspecialidadeId(1L);
        agendaDTO.setData(LocalDate.now());
        agendaDTO.setHora(LocalTime.now());
        agendaDTO.setJornada(Jornada.MANHA);
        agendaDTO.setStatusAgenda(StatusAgenda.ABERTO);

        agendaEntity = new AgendaEntity();
        agendaEntity.setProfissional(profissional);
        agendaEntity.setClinicaEntity(clinica);
        agendaEntity.setEspecialidadeProfissionalEntity(especialidade);
        agendaEntity.setData(LocalDate.now());
        agendaEntity.setHora(LocalTime.now());
        agendaEntity.setJornada(Jornada.MANHA);
        agendaEntity.setStatusAgenda(StatusAgenda.ABERTO);
    }

    /**
     * Testa o método gerarAgenda para verificar se ele gera uma agenda corretamente.
     */
    @Test
    void testGerarAgenda() {
        when(profissionalRepository.findById(1L)).thenReturn(java.util.Optional.of(profissional));
        when(clinicaRepository.findById(1L)).thenReturn(java.util.Optional.of(clinica));
        when(especialidadeProfissionalRepository.findById(1L)).thenReturn(java.util.Optional.of(especialidade));
        when(profissionaisClinicaRepository.existsByProfissionalIdAndClinicaEntityId(1L, 1L)).thenReturn(true);

        agendaService.gerarAgenda(1L, LocalDate.now(), 1L, 1L);

        verify(agendaRepository, times(1)).saveAll(anyList());
    }

    /**
     * Testa o método buscarAgendaPorProfissional para verificar se ele busca a agenda corretamente.
     */
    @Test
    void testBuscarAgendaPorProfissional() {
        List<AgendaEntity> agendaEntities = new ArrayList<>();
        agendaEntities.add(agendaEntity);

        when(agendaRepository.findByProfissionalId(1L)).thenReturn(agendaEntities);

        List<AgendaDTO> result = agendaService.buscarAgendaPorProfissional(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(agendaDTO.getProfissionalId(), result.get(0).getProfissionalId());
    }

    /**
     * Testa o método gerarAgenda para verificar se ele lança uma exceção corretamente.
     */
    @Test
    void testGerarAgendaThrowsException() {
        when(profissionaisClinicaRepository.existsByProfissionalIdAndClinicaEntityId(1L, 1L)).thenReturn(false);

        assertThrows(RegraDeNegocioException.class, () -> {
            agendaService.gerarAgenda(1L, LocalDate.now(), 1L, 1L);
        });
    }
}