package com.br.TechMed.service.imp.agendamento;

import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.dto.agendamento.AgendamentoDTO;
import com.br.TechMed.entity.agenda.AgendaEntity;
import com.br.TechMed.entity.agendamento.AgendamentoEntity;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.agenda.AgendaRepository;
import com.br.TechMed.repository.agendamento.AgendamentoRepository;
import com.br.TechMed.repository.cliente.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para AgendamentoServiceImp.
 * Utiliza JUnit 5 e Mockito para testar os métodos de AgendamentoServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceImpTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private AgendamentoServiceImp agendamentoService;

    private AgendamentoDTO agendamentoDTO;
    private AgendaEntity agendaEntity;
    private ClienteEntity clienteEntity;
    private AgendamentoEntity agendamentoEntity;

    /**
     * Configura os dados de teste antes de cada execução de teste.
     */
    @BeforeEach
    void setUp() {
        agendamentoDTO = new AgendamentoDTO();
        agendamentoDTO.setAgendaId(1L);
        agendamentoDTO.setClienteId(1L);

        agendaEntity = new AgendaEntity();
        agendaEntity.setId(1L);
        agendaEntity.setStatusAgenda(StatusAgenda.ABERTO);

        clienteEntity = new ClienteEntity();
        clienteEntity.setId(1L);

        agendamentoEntity = new AgendamentoEntity();
        agendamentoEntity.setId(1L);
        agendamentoEntity.setAgenda(agendaEntity);
        agendamentoEntity.setCliente(clienteEntity);
    }

    /**
     * Testa o método criarAgendamento para verificar se ele cria um agendamento corretamente.
     */
    @Test
    void testCriarAgendamento() {
        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agendaEntity));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEntity));
        when(agendamentoRepository.save(any(AgendamentoEntity.class))).thenReturn(agendamentoEntity);

        AgendamentoDTO result = agendamentoService.criarAgendamento(agendamentoDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(agendaRepository, times(1)).save(agendaEntity);
    }

    /**
     * Testa o método criarAgendamento para verificar se ele lança uma exceção quando a agenda não é encontrada.
     */
    @Test
    void testCriarAgendamentoThrowsExceptionWhenAgendaNotFound() {
        when(agendaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> {
            agendamentoService.criarAgendamento(agendamentoDTO);
        });
    }

    /**
     * Testa o método criarAgendamento para verificar se ele lança uma exceção quando o cliente não é encontrado.
     */
    @Test
    void testCriarAgendamentoThrowsExceptionWhenClienteNotFound() {
        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agendaEntity));
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> {
            agendamentoService.criarAgendamento(agendamentoDTO);
        });
    }

    /**
     * Testa o método criarAgendamento para verificar se ele lança uma exceção quando a agenda não está aberta.
     */
    @Test
    void testCriarAgendamentoThrowsExceptionWhenAgendaNotOpen() {
        agendaEntity.setStatusAgenda(StatusAgenda.AGENDADO);
        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agendaEntity));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEntity));

        assertThrows(RegraDeNegocioException.class, () -> {
            agendamentoService.criarAgendamento(agendamentoDTO);
        });
    }
}