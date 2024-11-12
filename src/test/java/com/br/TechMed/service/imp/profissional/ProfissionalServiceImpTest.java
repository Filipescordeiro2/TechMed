package com.br.TechMed.service.imp.profissional;

import com.br.TechMed.Enum.Especialidades;
import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.dto.agenda.AgendaDetalhadaDTO;
import com.br.TechMed.dto.profissional.LoginSenhaProfissionalDTO;
import com.br.TechMed.dto.profissional.ProfissionalDTO;
import com.br.TechMed.entity.agenda.AgendaEntity;
import com.br.TechMed.entity.profissional.EnderecoProfissionalEntity;
import com.br.TechMed.entity.profissional.EspecialidadeProfissionalEntity;
import com.br.TechMed.entity.profissional.ProfissionalEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.adm.AdmRepository;
import com.br.TechMed.repository.adm.ProfissionaisAdminRepository;
import com.br.TechMed.repository.agenda.AgendaRepository;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import com.br.TechMed.service.servicos.clinica.ProfissionaisClinicaService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para ProfissionalServiceImp.
 * Utiliza JUnit 5 e Mockito para testar os métodos de ProfissionalServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class ProfissionalServiceImpTest {

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ProfissionaisClinicaService profissionaisClinicaService;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private ProfissionaisAdminRepository profissionaisAdminRepository;

    @Mock
    private AdmRepository adminRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ProfissionalServiceImp profissionalService;

    private ProfissionalDTO profissionalDTO;
    private ProfissionalEntity profissionalEntity;
    private EnderecoProfissionalEntity enderecoProfissionalEntity;
    private EspecialidadeProfissionalEntity especialidadeProfissionalEntity;

    @BeforeEach
    void setUp() {
        profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setId(1L);
        profissionalDTO.setLogin("login");
        profissionalDTO.setSenha("senha");
        profissionalDTO.setNome("nome");
        profissionalDTO.setSobrenome("sobrenome");
        profissionalDTO.setEmail("email@example.com");
        profissionalDTO.setCpf("12345678900");
        profissionalDTO.setCelular("123456789");
        profissionalDTO.setAdminId(1L);

        enderecoProfissionalEntity = new EnderecoProfissionalEntity();
        enderecoProfissionalEntity.setCep("12345-678");
        enderecoProfissionalEntity.setLogradouro("Rua Exemplo");
        enderecoProfissionalEntity.setNumero("123");
        enderecoProfissionalEntity.setComplemento("Apto 1");
        enderecoProfissionalEntity.setBairro("Bairro Exemplo");
        enderecoProfissionalEntity.setCidade("Cidade Exemplo");
        enderecoProfissionalEntity.setEstado("Estado Exemplo");
        enderecoProfissionalEntity.setPais("Pais Exemplo");

        especialidadeProfissionalEntity = new EspecialidadeProfissionalEntity();
        especialidadeProfissionalEntity.setId(1L);
        especialidadeProfissionalEntity.setEspecialidades(Especialidades.valueOf("CARDIOLOGIA"));

        profissionalEntity = new ProfissionalEntity();
        profissionalEntity.setId(1L);
        profissionalEntity.setLogin("login");
        profissionalEntity.setSenha("senha");
        profissionalEntity.setNome("nome");
        profissionalEntity.setSobrenome("sobrenome");
        profissionalEntity.setEmail("email@example.com");
        profissionalEntity.setCpf("12345678900");
        profissionalEntity.setCelular("123456789");
        profissionalEntity.getEnderecos().add(enderecoProfissionalEntity);
        profissionalEntity.getEspecialidades().add(especialidadeProfissionalEntity);
    }

    @Test
    void testCadastrarProfissional() {
        when(adminRepository.existsById(1L)).thenReturn(true);
        when(profissionalRepository.save(any(ProfissionalEntity.class))).thenReturn(profissionalEntity);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        ProfissionalDTO result = profissionalService.cadastrarProfissional(profissionalDTO);

        assertNotNull(result);
        assertEquals(profissionalDTO.getId(), result.getId());
        assertEquals(profissionalDTO.getLogin(), result.getLogin());
        assertEquals(profissionalDTO.getSenha(), result.getSenha());
        assertEquals(profissionalDTO.getNome(), result.getNome());
        assertEquals(profissionalDTO.getSobrenome(), result.getSobrenome());
        assertEquals(profissionalDTO.getEmail(), result.getEmail());
        assertEquals(profissionalDTO.getCpf(), result.getCpf());
        assertEquals(profissionalDTO.getCelular(), result.getCelular());

        verify(profissionalRepository, times(1)).save(any(ProfissionalEntity.class));
    }

    @Test
    void testCadastrarProfissionalThrowsExceptionWhenAdminNotFound() {
        when(adminRepository.existsById(1L)).thenReturn(false);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            profissionalService.cadastrarProfissional(profissionalDTO);
        });

        assertEquals("Admin não encontrado", exception.getMessage());
    }

    @Test
    void testAutenticarProfissional() {
        when(profissionalRepository.findByLogin("login")).thenReturn(Optional.of(profissionalEntity));

        LoginSenhaProfissionalDTO loginSenhaProfissionalDTO = new LoginSenhaProfissionalDTO();
        loginSenhaProfissionalDTO.setLogin("login");
        loginSenhaProfissionalDTO.setSenha("senha");

        ProfissionalDTO result = profissionalService.autenticarProfissional(loginSenhaProfissionalDTO);

        assertNotNull(result);
        assertEquals(profissionalDTO.getLogin(), result.getLogin());
        assertEquals(profissionalDTO.getSenha(), result.getSenha());
    }

    @Test
    void testAutenticarProfissionalThrowsExceptionWhenLoginInvalid() {
        when(profissionalRepository.findByLogin("login")).thenReturn(Optional.empty());

        LoginSenhaProfissionalDTO loginSenhaProfissionalDTO = new LoginSenhaProfissionalDTO();
        loginSenhaProfissionalDTO.setLogin("login");
        loginSenhaProfissionalDTO.setSenha("senha");

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            profissionalService.autenticarProfissional(loginSenhaProfissionalDTO);
        });

        assertEquals("Login ou senha inválidos", exception.getMessage());
    }

    @Test
    void testAutenticarProfissionalThrowsExceptionWhenSenhaInvalid() {
        when(profissionalRepository.findByLogin("login")).thenReturn(Optional.of(profissionalEntity));

        LoginSenhaProfissionalDTO loginSenhaProfissionalDTO = new LoginSenhaProfissionalDTO();
        loginSenhaProfissionalDTO.setLogin("login");
        loginSenhaProfissionalDTO.setSenha("wrongSenha");

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            profissionalService.autenticarProfissional(loginSenhaProfissionalDTO);
        });

        assertEquals("Login ou senha inválidos", exception.getMessage());
    }

    @Test
    void testGetAgendaByProfissional() {
        AgendaEntity agendaEntity = new AgendaEntity();
        agendaEntity.setId(1L);
        agendaEntity.setStatusAgenda(StatusAgenda.ABERTO);
        agendaEntity.setData(LocalDate.now());
        agendaEntity.setHora(LocalTime.now());
        agendaEntity.setProfissional(profissionalEntity);

        when(profissionalRepository.existsById(1L)).thenReturn(true);
        when(agendaRepository.findByProfissionalId(1L)).thenReturn(Collections.singletonList(agendaEntity));

        List<AgendaDetalhadaDTO> result = profissionalService.getAgendaByProfissional(1L, 1L, "ABERTO", LocalDate.now(), LocalTime.now(), "nome");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(agendaEntity.getId(), result.get(0).getCodigoAgenda());
    }

    @Test
    void testGetAgendaByProfissionalThrowsExceptionWhenProfissionalNotFound() {
        when(profissionalRepository.existsById(1L)).thenReturn(false);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            profissionalService.getAgendaByProfissional(1L, 1L, "ABERTO", LocalDate.now(), LocalTime.now(), "nome");
        });

        assertEquals("Profissional não encontrado", exception.getMessage());
    }
}