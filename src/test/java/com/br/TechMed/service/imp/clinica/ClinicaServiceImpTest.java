package com.br.TechMed.service.imp.clinica;

import com.br.TechMed.Enum.Especialidades;
import com.br.TechMed.dto.Clinica.ClinicaDTO;
import com.br.TechMed.dto.Clinica.EnderecoClinicaDTO;
import com.br.TechMed.dto.Clinica.EspecialidadeClinicaDTO;
import com.br.TechMed.entity.adm.ClinicasAdminEntity;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.clinica.EnderecoClinicaEntity;
import com.br.TechMed.entity.clinica.EspecialidadeClinicaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.adm.AdmRepository;
import com.br.TechMed.repository.adm.ClinicaAdminRepository;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.service.servicos.clinica.ProfissionaisClinicaService;
import jakarta.servlet.http.HttpServletRequest;
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
 * Classe de teste para ClinicaServiceImp.
 * Utiliza JUnit 5 e Mockito para testar os métodos de ClinicaServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class ClinicaServiceImpTest {

    @Mock
    private ClinicaRepository clinicaRepository;

    @Mock
    private ProfissionaisClinicaService profissionaisClinicaService;

    @Mock
    private ClinicaAdminRepository clinicaAdminRepository;

    @Mock
    private AdmRepository adminRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ClinicaServiceImp clinicaService;

    private ClinicaDTO clinicaDTO;
    private ClinicaEntity clinicaEntity;
    private EnderecoClinicaEntity enderecoClinicaEntity;
    private EspecialidadeClinicaEntity especialidadeClinicaEntity;

    @BeforeEach
    void setUp() {
        clinicaDTO = new ClinicaDTO();
        clinicaDTO.setAdminId(1L);
        clinicaDTO.setNomeClinica("Clinica Teste");
        clinicaDTO.setDescricaoClinica("Descricao Teste");
        clinicaDTO.setTelefone("123456789");
        clinicaDTO.setCelular("987654321");
        clinicaDTO.setEmail("clinica@teste.com");
        clinicaDTO.setCnpj("12345678000100");

        EnderecoClinicaDTO enderecoClinicaDTO = new EnderecoClinicaDTO();
        enderecoClinicaDTO.setCep("12345-678");
        enderecoClinicaDTO.setLogradouro("Rua Teste");
        enderecoClinicaDTO.setNumero("123");
        enderecoClinicaDTO.setComplemento("Apto 1");
        enderecoClinicaDTO.setBairro("Bairro Teste");
        enderecoClinicaDTO.setCidade("Cidade Teste");
        enderecoClinicaDTO.setEstado("Estado Teste");
        enderecoClinicaDTO.setPais("Pais Teste");
        clinicaDTO.setEnderecoClinica(enderecoClinicaDTO);

        EspecialidadeClinicaDTO especialidadeClinicaDTO = new EspecialidadeClinicaDTO();
        especialidadeClinicaDTO.setEspecialidades(Especialidades.valueOf("CARDIOLOGISTA"));
        clinicaDTO.setEspecialidadeClinica(Collections.singletonList(especialidadeClinicaDTO));

        clinicaEntity = new ClinicaEntity();
        clinicaEntity.setId(1L);
        clinicaEntity.setNomeClinica("Clinica Teste");
        clinicaEntity.setDescricaoClinica("Descricao Teste");
        clinicaEntity.setTelefone("123456789");
        clinicaEntity.setCelular("987654321");
        clinicaEntity.setEmail("clinica@teste.com");
        clinicaEntity.setCnpj("12345678000100");

        enderecoClinicaEntity = new EnderecoClinicaEntity();
        enderecoClinicaEntity.setCep("12345-678");
        enderecoClinicaEntity.setLogradouro("Rua Teste");
        enderecoClinicaEntity.setNumero("123");
        enderecoClinicaEntity.setComplemento("Apto 1");
        enderecoClinicaEntity.setBairro("Bairro Teste");
        enderecoClinicaEntity.setCidade("Cidade Teste");
        enderecoClinicaEntity.setEstado("Estado Teste");
        enderecoClinicaEntity.setPais("Pais Teste");
        enderecoClinicaEntity.setClinicaEntity(clinicaEntity);
        clinicaEntity.getEnderecos().add(enderecoClinicaEntity);

        especialidadeClinicaEntity = new EspecialidadeClinicaEntity();
        especialidadeClinicaEntity.setEspecialidades(Especialidades.valueOf("CARDIOLOGISTA"));
        especialidadeClinicaEntity.setClinicaEntity(clinicaEntity);
        clinicaEntity.getEspecialidades().add(especialidadeClinicaEntity);
    }

    @Test
    void testCadastrarClinica() {
        when(adminRepository.existsById(1L)).thenReturn(true);
        when(clinicaRepository.save(any(ClinicaEntity.class))).thenReturn(clinicaEntity);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        ClinicaDTO result = clinicaService.cadastrarClinica(clinicaDTO);

        assertNotNull(result);
        assertEquals(clinicaDTO.getNomeClinica(), result.getNomeClinica());
        assertEquals(clinicaDTO.getDescricaoClinica(), result.getDescricaoClinica());
        assertEquals(clinicaDTO.getTelefone(), result.getTelefone());
        assertEquals(clinicaDTO.getCelular(), result.getCelular());
        assertEquals(clinicaDTO.getEmail(), result.getEmail());
        assertEquals(clinicaDTO.getCnpj(), result.getCnpj());

        verify(clinicaRepository, times(1)).save(any(ClinicaEntity.class));
        verify(clinicaAdminRepository, times(1)).save(any(ClinicasAdminEntity.class));
    }

    @Test
    void testCadastrarClinicaThrowsExceptionWhenAdminNotFound() {
        when(adminRepository.existsById(1L)).thenReturn(false);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            clinicaService.cadastrarClinica(clinicaDTO);
        });

        assertEquals("Admin não encontrado", exception.getMessage());
    }

    @Test
    void testListarTodasClinicas() {
        when(clinicaRepository.findAll()).thenReturn(Collections.singletonList(clinicaEntity));

        List<ClinicaDTO> result = clinicaService.listarTodasClinicas();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(clinicaDTO.getNomeClinica(), result.get(0).getNomeClinica());
    }
}