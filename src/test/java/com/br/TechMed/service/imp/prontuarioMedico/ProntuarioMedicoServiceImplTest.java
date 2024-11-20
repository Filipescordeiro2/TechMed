package com.br.TechMed.service.imp.prontuarioMedico;

import com.br.TechMed.dto.prontuarioMedico.ProtuarioMedicoDTO;
import com.br.TechMed.dto.prontuarioMedico.ProtuarioMedicoDetalhadoDTO;
import com.br.TechMed.entity.cliente.ClienteEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.entity.protuarioMedico.ProtuarioMedicoEntity;
import com.br.TechMed.repository.cliente.ClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ExamesClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.MedicamentosClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ProcedimentosClienteRepository;
import com.br.TechMed.repository.prontuarioMedico.ProtuarioMedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para ProntuarioMedicoServiceImpl.
 * Utiliza JUnit 5 e Mockito para testar os métodos de ProntuarioMedicoServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
public class ProntuarioMedicoServiceImplTest {

    @Mock
    private ProtuarioMedicoRepository protuarioMedicoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ExamesClienteRepository examesClienteRepository;

    @Mock
    private ProcedimentosClienteRepository procedimentosClienteRepository;

    @Mock
    private MedicamentosClienteRepository medicamentosClienteRepository;

    @InjectMocks
    private ProntuarioMedicoServiceImpl prontuarioMedicoService;

    private ProtuarioMedicoDTO prontuarioMedicoDTO;
    private ProtuarioMedicoEntity protuarioMedicoEntity;
    private ClienteEntity clienteEntity;
    private ProfissionalEntity profissionalEntity;

    @BeforeEach
    void setUp() {
        prontuarioMedicoDTO = new ProtuarioMedicoDTO();
        prontuarioMedicoDTO.setCpf("12345678900");
        prontuarioMedicoDTO.setDescricao("Consulta de rotina");
        prontuarioMedicoDTO.setDataConsulta(LocalDate.parse("2023-10-10"));
        prontuarioMedicoDTO.setProfissionalId(1L);
        prontuarioMedicoDTO.setExames(Collections.emptyList()); // Initialize examesDTO
        prontuarioMedicoDTO.setProcedimentos(Collections.emptyList()); // Initialize procedimentosDTO
        prontuarioMedicoDTO.setMedicamentos(Collections.emptyList()); // Initialize medicamentosDTO

        clienteEntity = new ClienteEntity();
        clienteEntity.setId(1L);
        clienteEntity.setCpf("12345678900");

        profissionalEntity = new ProfissionalEntity();
        profissionalEntity.setId(1L);

        protuarioMedicoEntity = new ProtuarioMedicoEntity();
        protuarioMedicoEntity.setId(1L);
        protuarioMedicoEntity.setDescricao("Consulta de rotina");
        protuarioMedicoEntity.setDataConsulta(LocalDate.parse("2023-10-10"));
        protuarioMedicoEntity.setCliente(clienteEntity);
        protuarioMedicoEntity.setProfissional(profissionalEntity);
        protuarioMedicoEntity.setExames(Collections.emptyList()); // Initialize exames
        protuarioMedicoEntity.setProcedimentos(Collections.emptyList()); // Initialize procedimentos
        protuarioMedicoEntity.setMedicamentos(Collections.emptyList()); // Initialize medicamentos
    }

    @Test
    void testSave() {
        when(clienteRepository.findByCpf("12345678900")).thenReturn(Optional.of(clienteEntity));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteEntity));
        when(protuarioMedicoRepository.save(any(ProtuarioMedicoEntity.class))).thenReturn(protuarioMedicoEntity);

        ProtuarioMedicoDTO result = prontuarioMedicoService.save(prontuarioMedicoDTO);

        assertNotNull(result);
        assertEquals(prontuarioMedicoDTO.getDescricao(), result.getDescricao());
        assertEquals(prontuarioMedicoDTO.getDataConsulta(), result.getDataConsulta());
        assertEquals(prontuarioMedicoDTO.getCpf(), result.getCpf());

        verify(protuarioMedicoRepository, times(1)).save(any(ProtuarioMedicoEntity.class));
    }

    @Test
    void testSaveThrowsExceptionWhenCpfIsMissing() {
        prontuarioMedicoDTO.setCpf(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            prontuarioMedicoService.save(prontuarioMedicoDTO);
        });

        assertEquals("CPF do cliente é obrigatório", exception.getMessage());
    }

    @Test
    void testFindByClienteId() {
        when(protuarioMedicoRepository.findByClienteId(1L)).thenReturn(Collections.singletonList(protuarioMedicoEntity));

        List<ProtuarioMedicoDetalhadoDTO> result = prontuarioMedicoService.findByClienteId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(prontuarioMedicoDTO.getDescricao(), result.get(0).getDescricao());
    }
}