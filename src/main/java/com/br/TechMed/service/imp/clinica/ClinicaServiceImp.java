package com.br.TechMed.service.imp.clinica;

import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.Enum.TipoUsuario;
import com.br.TechMed.dto.Clinica.ClinicaDTO;
import com.br.TechMed.dto.Clinica.EnderecoClinicaDTO;
import com.br.TechMed.dto.Clinica.EspecialidadeClinicaDTO;
import com.br.TechMed.dto.Clinica.ProfissionaisClinicaDTO;
import com.br.TechMed.entity.adm.ClinicasAdminEntity;
import com.br.TechMed.entity.clinica.ClinicaEntity;
import com.br.TechMed.entity.clinica.EnderecoClinicaEntity;
import com.br.TechMed.entity.clinica.EspecialidadeClinicaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.adm.AdmRepository;
import com.br.TechMed.repository.adm.ClinicaAdminRepository;
import com.br.TechMed.repository.clinica.ClinicaRepository;
import com.br.TechMed.service.servicos.clinica.ClinicaService;
import com.br.TechMed.service.servicos.clinica.ProfissionaisClinicaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da interface ClinicaService.
 * Fornece métodos para gerenciar clínicas.
 */
@Service
public class ClinicaServiceImp implements ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private ProfissionaisClinicaService profissionaisClinicaService;

    @Autowired
    private ClinicaAdminRepository clinicaAdminRepository;

    @Autowired
    private AdmRepository adminRepository;

    @Autowired
    private HttpServletRequest request;

    /**
     * Cadastra uma nova clínica no sistema.
     *
     * @param clinicaDTO os dados da clínica a ser cadastrada
     * @return os dados da clínica cadastrada
     */
    @Override
    @Transactional
    public ClinicaDTO cadastrarClinica(ClinicaDTO clinicaDTO) {
        try {
            // Valida se o adminId é válido
            if (!adminRepository.existsById(clinicaDTO.getAdminId())) {
                throw new RegraDeNegocioException("Admin não encontrado");
            }

            ClinicaEntity clinicaEntity = fromDto(clinicaDTO);

            try {
                EnderecoClinicaEntity enderecoEntity = fromDto(clinicaDTO.getEnderecoClinica());
                clinicaEntity.getEnderecos().add(enderecoEntity);
                enderecoEntity.setClinicaEntity(clinicaEntity);
            } catch (Exception e) {
                throw new RegraDeNegocioException("Erro ao processar o endereço da clínica");
            }

            try {
                List<EspecialidadeClinicaEntity> especialidadeEntities = clinicaDTO.getEspecialidadeClinica().stream()
                        .map(this::fromDto)
                        .collect(Collectors.toList());
                clinicaEntity.getEspecialidades().addAll(especialidadeEntities);
                final ClinicaEntity finalClinicaEntity = clinicaEntity;
                especialidadeEntities.forEach(especialidade -> especialidade.setClinicaEntity(finalClinicaEntity));
            } catch (Exception e) {
                throw new RegraDeNegocioException("Erro ao processar as especialidades da clínica");
            }

            clinicaEntity = clinicaRepository.save(clinicaEntity);

            // Captura o endereço IP
            String ipAddress = request.getRemoteAddr();

            // Formata a hora
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = LocalTime.now().format(timeFormatter);

            // Salva a relação entre clínica e admin
            ClinicasAdminEntity clinicasAdminEntity = new ClinicasAdminEntity();
            clinicasAdminEntity.setClinicaId(clinicaEntity.getId());
            clinicasAdminEntity.setAdminId(clinicaDTO.getAdminId());
            clinicasAdminEntity.setDataCriacao(LocalDate.now());
            clinicasAdminEntity.setHoraCriacao(LocalTime.parse(formattedTime, timeFormatter));
            clinicasAdminEntity.setIpAddress(ipAddress);

            clinicaAdminRepository.save(clinicasAdminEntity);

            return toDto(clinicaEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao cadastrar a clínica");
        }
    }

    /**
     * Converte uma entidade ClinicaEntity para um DTO ClinicaDTO.
     *
     * @param clinicaEntity a entidade da clínica
     * @return o DTO da clínica
     */
    private ClinicaDTO toDto(ClinicaEntity clinicaEntity) {
        ClinicaDTO clinicaDTO = new ClinicaDTO();
        clinicaDTO.setId(clinicaEntity.getId());
        clinicaDTO.setNomeClinica(clinicaEntity.getNomeClinica());
        clinicaDTO.setDescricaoClinica(clinicaEntity.getDescricaoClinica());
        clinicaDTO.setTelefone(clinicaEntity.getTelefone());
        clinicaDTO.setCelular(clinicaEntity.getCelular());
        clinicaDTO.setEmail(clinicaEntity.getEmail());
        clinicaDTO.setCnpj(clinicaEntity.getCnpj());
        clinicaDTO.setStatusClinica(clinicaEntity.getStatusClinica());
        clinicaDTO.setTipoUsuario(clinicaEntity.getTipoUsuario());

        if (!clinicaEntity.getEnderecos().isEmpty()) {
            clinicaDTO.setEnderecoClinica(toDto(clinicaEntity.getEnderecos().get(0)));
        }

        if (!clinicaEntity.getEspecialidades().isEmpty()) {
            List<EspecialidadeClinicaDTO> especialidadeDTOs = clinicaEntity.getEspecialidades().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
            clinicaDTO.setEspecialidadeClinica(especialidadeDTOs);
        }

        List<ProfissionaisClinicaDTO> profissionaisClinicaDTOs = profissionaisClinicaService.findByClinicaId(clinicaEntity.getId());
        clinicaDTO.setProfissionaisClinica(profissionaisClinicaDTOs);

        return clinicaDTO;
    }

    /**
     * Converte um DTO ClinicaDTO para uma entidade ClinicaEntity.
     *
     * @param clinicaDTO o DTO da clínica
     * @return a entidade da clínica
     */
    private ClinicaEntity fromDto(ClinicaDTO clinicaDTO) {
        ClinicaEntity clinicaEntity = new ClinicaEntity();
        clinicaEntity.setNomeClinica(clinicaDTO.getNomeClinica());
        clinicaEntity.setDescricaoClinica(clinicaDTO.getDescricaoClinica());
        clinicaEntity.setTelefone(clinicaDTO.getTelefone());
        clinicaEntity.setCelular(clinicaDTO.getCelular());
        clinicaEntity.setEmail(clinicaDTO.getEmail());
        clinicaEntity.setCnpj(clinicaDTO.getCnpj());
        clinicaEntity.setStatusClinica(StatusUsuario.ATIVO);
        clinicaEntity.setTipoUsuario(TipoUsuario.CLINICA);
        return clinicaEntity;
    }

    /**
     * Converte uma entidade EnderecoClinicaEntity para um DTO EnderecoClinicaDTO.
     *
     * @param enderecoEntity a entidade do endereço da clínica
     * @return o DTO do endereço da clínica
     */
    private EnderecoClinicaDTO toDto(EnderecoClinicaEntity enderecoEntity) {
        EnderecoClinicaDTO enderecoDTO = new EnderecoClinicaDTO();
        enderecoDTO.setId(enderecoEntity.getId());
        enderecoDTO.setCep(enderecoEntity.getCep());
        enderecoDTO.setLogradouro(enderecoEntity.getLogradouro());
        enderecoDTO.setNumero(enderecoEntity.getNumero());
        enderecoDTO.setComplemento(enderecoEntity.getComplemento());
        enderecoDTO.setBairro(enderecoEntity.getBairro());
        enderecoDTO.setCidade(enderecoEntity.getCidade());
        enderecoDTO.setEstado(enderecoEntity.getEstado());
        enderecoDTO.setPais(enderecoEntity.getPais());
        return enderecoDTO;
    }

    /**
     * Converte um DTO EnderecoClinicaDTO para uma entidade EnderecoClinicaEntity.
     *
     * @param enderecoDTO o DTO do endereço da clínica
     * @return a entidade do endereço da clínica
     */
    private EnderecoClinicaEntity fromDto(EnderecoClinicaDTO enderecoDTO) {
        EnderecoClinicaEntity enderecoEntity = new EnderecoClinicaEntity();
        enderecoEntity.setCep(enderecoDTO.getCep());
        enderecoEntity.setLogradouro(enderecoDTO.getLogradouro());
        enderecoEntity.setNumero(enderecoDTO.getNumero());
        enderecoEntity.setComplemento(enderecoDTO.getComplemento());
        enderecoEntity.setBairro(enderecoDTO.getBairro());
        enderecoEntity.setCidade(enderecoDTO.getCidade());
        enderecoEntity.setEstado(enderecoDTO.getEstado());
        enderecoEntity.setPais(enderecoDTO.getPais());
        return enderecoEntity;
    }

    /**
     * Converte uma entidade EspecialidadeClinicaEntity para um DTO EspecialidadeClinicaDTO.
     *
     * @param especialidadeClinicaEntity a entidade da especialidade da clínica
     * @return o DTO da especialidade da clínica
     */
    private EspecialidadeClinicaDTO toDto(EspecialidadeClinicaEntity especialidadeClinicaEntity) {
        EspecialidadeClinicaDTO especialidadeClinicaDTO = new EspecialidadeClinicaDTO();
        especialidadeClinicaDTO.setId(especialidadeClinicaEntity.getId());
        especialidadeClinicaDTO.setEspecialidades(especialidadeClinicaEntity.getEspecialidades());
        return especialidadeClinicaDTO;
    }

    /**
     * Converte um DTO EspecialidadeClinicaDTO para uma entidade EspecialidadeClinicaEntity.
     *
     * @param especialidadeClinicaDTO o DTO da especialidade da clínica
     * @return a entidade da especialidade da clínica
     */
    private EspecialidadeClinicaEntity fromDto(EspecialidadeClinicaDTO especialidadeClinicaDTO) {
        EspecialidadeClinicaEntity especialidadeClinicaEntity = new EspecialidadeClinicaEntity();
        especialidadeClinicaEntity.setEspecialidades(especialidadeClinicaDTO.getEspecialidades());
        return especialidadeClinicaEntity;
    }

    /**
     * Retorna todas as clínicas cadastradas.
     *
     * @return lista de DTOs de clínicas
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClinicaDTO> listarTodasClinicas() {
        List<ClinicaEntity> clinicas = clinicaRepository.findAll().stream()
                .filter(clinica -> clinica.getStatusClinica() == StatusUsuario.ATIVO)
                .collect(Collectors.toList());
        return clinicas.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public long contarClinicas() {
        return clinicaRepository.findAll().stream()
                .filter(clinica -> clinica.getStatusClinica() == StatusUsuario.ATIVO)
                .count();
    }

    @Override
    @Transactional
    public void atualizarStatusClinica(Long id) {
        ClinicaEntity clinicaEntity = clinicaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Clínica não encontrada"));
        clinicaEntity.setStatusClinica(StatusUsuario.INATIVO);
        clinicaRepository.save(clinicaEntity);
    }


}