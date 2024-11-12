package com.br.TechMed.service.imp.adm;

import com.br.TechMed.dto.adm.AdminDTO;
import com.br.TechMed.entity.adm.AdminEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.adm.AdmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para a implementação do serviço de administração.
 * Utiliza JUnit 5 e Mockito para testar os métodos da classe admServiceImp.
 */
@ExtendWith(MockitoExtension.class)
public class admServiceImpTest {

    @Mock
    private AdmRepository admRepository;

    @InjectMocks
    private admServiceImp admService;

    private AdminDTO adminDTO;
    private AdminEntity adminEntity;

    /**
     * Configura os objetos de teste antes de cada execução de teste.
     */
    @BeforeEach
    void setUp() {
        adminDTO = new AdminDTO();
        adminDTO.setLogin("admin");
        adminDTO.setSenha("password");
        adminDTO.setNome("Admin");
        adminDTO.setSobrenome("User");
        adminDTO.setEmail("admin@example.com");
        adminDTO.setCpf("12345678900");
        adminDTO.setCelular("123456789");

        adminEntity = new AdminEntity();
        adminEntity.setLogin("admin");
        adminEntity.setSenha("password");
        adminEntity.setNome("Admin");
        adminEntity.setSobrenome("User");
        adminEntity.setEmail("admin@example.com");
        adminEntity.setCpf("12345678900");
        adminEntity.setCelular("123456789");
    }

    /**
     * Testa o método cadastrarAdmin para verificar se ele cadastra um administrador corretamente.
     */
    @Test
    void testCadastrarAdmin() {
        // Configura o comportamento do mock
        when(admRepository.save(any(AdminEntity.class))).thenReturn(adminEntity);

        // Executa o método a ser testado
        AdminDTO result = admService.cadastrarAdmin(adminDTO);

        // Verifica os resultados
        assertNotNull(result);
        assertEquals(adminDTO.getLogin(), result.getLogin());
        assertEquals(adminDTO.getSenha(), result.getSenha());
        assertEquals(adminDTO.getNome(), result.getNome());
        assertEquals(adminDTO.getSobrenome(), result.getSobrenome());
        assertEquals(adminDTO.getEmail(), result.getEmail());
        assertEquals(adminDTO.getCpf(), result.getCpf());
        assertEquals(adminDTO.getCelular(), result.getCelular());

        // Verifica se o método save foi chamado uma vez
        verify(admRepository, times(1)).save(any(AdminEntity.class));
    }

    /**
     * Testa o método cadastrarAdmin para verificar se ele lança uma exceção corretamente.
     */
    @Test
    void testCadastrarAdminThrowsException() {
        // Configura o comportamento do mock para lançar uma exceção
        when(admRepository.save(any(AdminEntity.class))).thenThrow(new RuntimeException("Database error"));

        // Verifica se a exceção é lançada
        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> {
            admService.cadastrarAdmin(adminDTO);
        });

        // Verifica a mensagem da exceção
        assertEquals("Erro ao cadastrar admin: Database error", exception.getMessage());

        // Verifica se o método save foi chamado uma vez
        verify(admRepository, times(1)).save(any(AdminEntity.class));
    }
}