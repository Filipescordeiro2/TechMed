package com.br.TechMed.service.imp.adm;

import com.br.TechMed.dto.adm.AdminDTO;
import com.br.TechMed.dto.adm.LoginSenhaAdminDTO;
import com.br.TechMed.entity.adm.AdminEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.adm.AdmRepository;
import com.br.TechMed.service.servicos.adm.AdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class admServiceImp implements AdmService {

    @Autowired
    private AdmRepository admRepository;

    @Override
    @Transactional
    public AdminDTO cadastrarAdmin(AdminDTO adminDTO) {
        try {
            AdminEntity adminEntity = fromDto(adminDTO);
            adminEntity = admRepository.save(adminEntity);

            return toDto(adminEntity);

        } catch (Exception e) {
            System.err.println("Erro ao cadastrar admin: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao cadastrar admin: " + e.getMessage());
        }
    }

    @Override
    public AdminDTO autenticarAdmin(LoginSenhaAdminDTO loginSenhaAdminDTO) {
        AdminEntity adminEntity = admRepository.findByLogin(loginSenhaAdminDTO.getLogin())
                .orElseThrow(() -> new RegraDeNegocioException("Login ou senha inválidos"));

        if (!adminEntity.getSenha().equals(loginSenhaAdminDTO.getSenha())) {
            throw new RegraDeNegocioException("Login ou senha inválidos");
        }

        return toDto(adminEntity);
    }
    /**
     * Converte uma entidade AdminEntity para um DTO AdminDTO.
     *
     * @param adminEntity a entidade do Admin
     * @return o DTO do Admin
     */
    private AdminDTO toDto(AdminEntity adminEntity) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId(adminEntity.getId());
        adminDTO.setSenha(adminEntity.getSenha());
        adminDTO.setNome(adminEntity.getNome());
        adminDTO.setSobrenome(adminEntity.getSobrenome());
        adminDTO.setEmail(adminEntity.getEmail());
        adminDTO.setCpf(adminEntity.getCpf());
        adminDTO.setCelular(adminEntity.getCelular());
        return adminDTO;
    }

    /**
     * Converte um DTO AdminDTO para uma entidade AdminEntity.
     *
     * @param adminDTO o DTO do Admin
     * @return a entidade do Admin
     */
    private AdminEntity fromDto(AdminDTO adminDTO) {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setLogin(adminDTO.getEmail());
        adminEntity.setSenha(adminDTO.getSenha());
        adminEntity.setNome(adminDTO.getNome());
        adminEntity.setSobrenome(adminDTO.getSobrenome());
        adminEntity.setEmail(adminDTO.getEmail());
        adminEntity.setCpf(adminDTO.getCpf());
        adminEntity.setCelular(adminDTO.getCelular());
        return adminEntity;
    }



}
