package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.dto.*;
import com.br.clinicaSantana.entity.*;
import com.br.clinicaSantana.exception.RegraDeNegocioException;
import com.br.clinicaSantana.repository.AgendaRepository;
import com.br.clinicaSantana.repository.ProfissionalRepository;
import com.br.clinicaSantana.service.ProfissionalService;
import com.br.clinicaSantana.service.ProfissionaisClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da interface ProfissionalService.
 * Fornece métodos para gerenciar profissionais.
 */
@Service
public class ProfissionalServiceImp implements ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ProfissionaisClinicaService profissionaisClinicaService;

    @Autowired
    private AgendaRepository agendaRepository;

    /**
     * Cadastra um novo profissional no sistema.
     *
     * @param profissionalDTO os dados do profissional a ser cadastrado
     * @return os dados do profissional cadastrado
     */
    @Override
    @Transactional
    public ProfissionalDTO cadastrarProfissional(ProfissionalDTO profissionalDTO) {
        try {
            ProfissionalEntity profissionalEntity = fromDto(profissionalDTO);

            // Processa endereços
            List<EnderecoProfissionalEntity> enderecoEntities = profissionalDTO.getEnderecos().stream()
                    .map(this::fromDto)
                    .collect(Collectors.toList());
            profissionalEntity.getEnderecos().addAll(enderecoEntities);
            ProfissionalEntity finalProfissionalEntity = profissionalEntity;
            enderecoEntities.forEach(endereco -> endereco.setProfissionalEntity(finalProfissionalEntity));

            // Processa especialidades
            List<EspecialidadeProfissionalEntity> especialidadeEntities = profissionalDTO.getEspecialidades().stream()
                    .map(this::fromDto)
                    .collect(Collectors.toList());
            profissionalEntity.getEspecialidades().addAll(especialidadeEntities);
            ProfissionalEntity finalProfissionalEntity1 = profissionalEntity;
            especialidadeEntities.forEach(especialidade -> especialidade.setProfissionalEntity(finalProfissionalEntity1));

            // Salva profissional
            profissionalEntity = profissionalRepository.save(profissionalEntity);

            // Processa clínicas
            List<ProfissionaisClinicaDTO> clinicas = profissionalDTO.getClinicas();
            if (clinicas != null) {
                ProfissionalEntity finalProfissionalEntity2 = profissionalEntity;
                clinicas.forEach(clinicaDTO -> {
                    clinicaDTO.setProfissionalId(finalProfissionalEntity2.getId());
                    profissionaisClinicaService.save(clinicaDTO);
                });
            }

            return toDto(profissionalEntity);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao cadastrar o profissional");
        }
    }

    /**
     * Converte uma entidade ProfissionalEntity para um DTO ProfissionalDTO.
     *
     * @param profissionalEntity a entidade do profissional
     * @return o DTO do profissional
     */
    private ProfissionalDTO toDto(ProfissionalEntity profissionalEntity) {
        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setId(profissionalEntity.getId());
        profissionalDTO.setLogin(profissionalEntity.getLogin());
        profissionalDTO.setSenha(profissionalEntity.getSenha());
        profissionalDTO.setNome(profissionalEntity.getNome());
        profissionalDTO.setSobrenome(profissionalEntity.getSobrenome());
        profissionalDTO.setEmail(profissionalEntity.getEmail());
        profissionalDTO.setCpf(profissionalEntity.getCpf());
        profissionalDTO.setCelular(profissionalEntity.getCelular());

        if (!profissionalEntity.getEnderecos().isEmpty()) {
            List<EnderecoProfissionalDTO> enderecoDTOs = profissionalEntity.getEnderecos().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
            profissionalDTO.setEnderecos(enderecoDTOs);
        }

        if (!profissionalEntity.getEspecialidades().isEmpty()) {
            List<EspecialidadeProfissionalDTO> especialidadeDTOs = profissionalEntity.getEspecialidades().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
            profissionalDTO.setEspecialidades(especialidadeDTOs);
        }

        List<ProfissionaisClinicaDTO> clinicaDTOs = profissionaisClinicaService.findByProfissionalId(profissionalEntity.getId());
        profissionalDTO.setClinicas(clinicaDTOs);

        return profissionalDTO;
    }

    @Override
    public ProfissionalDTO autenticarProfissional(LoginSenhaProfissionalDTO loginSenhaProfissionalDTO) {
        ProfissionalEntity profissionalEntity = profissionalRepository.findByLogin(loginSenhaProfissionalDTO.getLogin())
                .orElseThrow(() -> new RegraDeNegocioException("Login ou senha inválidos"));

        if (!profissionalEntity.getSenha().equals(loginSenhaProfissionalDTO.getSenha())) {
            throw new RegraDeNegocioException("Login ou senha inválidos");
        }

        return toDto(profissionalEntity);
    }

    /**
     * Converte um DTO ProfissionalDTO para uma entidade ProfissionalEntity.
     *
     * @param profissionalDTO o DTO do profissional
     * @return a entidade do profissional
     */
    private ProfissionalEntity fromDto(ProfissionalDTO profissionalDTO) {
        ProfissionalEntity profissionalEntity = new ProfissionalEntity();
        profissionalEntity.setLogin(profissionalDTO.getLogin());
        profissionalEntity.setSenha(profissionalDTO.getSenha());
        profissionalEntity.setNome(profissionalDTO.getNome());
        profissionalEntity.setSobrenome(profissionalDTO.getSobrenome());
        profissionalEntity.setEmail(profissionalDTO.getEmail());
        profissionalEntity.setCpf(profissionalDTO.getCpf());
        profissionalEntity.setCelular(profissionalDTO.getCelular());
        return profissionalEntity;
    }

    /**
     * Converte uma entidade EnderecoProfissionalEntity para um DTO EnderecoProfissionalDTO.
     *
     * @param enderecoEntity a entidade do endereço do profissional
     * @return o DTO do endereço do profissional
     */
    private EnderecoProfissionalDTO toDto(EnderecoProfissionalEntity enderecoEntity) {
        EnderecoProfissionalDTO enderecoDTO = new EnderecoProfissionalDTO();
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
     * Converte um DTO EnderecoProfissionalDTO para uma entidade EnderecoProfissionalEntity.
     *
     * @param enderecoDTO o DTO do endereço do profissional
     * @return a entidade do endereço do profissional
     */
    private EnderecoProfissionalEntity fromDto(EnderecoProfissionalDTO enderecoDTO) {
        EnderecoProfissionalEntity enderecoEntity = new EnderecoProfissionalEntity();
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
     * Converte uma entidade EspecialidadeProfissionalEntity para um DTO EspecialidadeProfissionalDTO.
     *
     * @param especialidadeEntity a entidade da especialidade do profissional
     * @return o DTO da especialidade do profissional
     */
    private EspecialidadeProfissionalDTO toDto(EspecialidadeProfissionalEntity especialidadeEntity) {
        EspecialidadeProfissionalDTO especialidadeDTO = new EspecialidadeProfissionalDTO();
        especialidadeDTO.setId(especialidadeEntity.getId());
        especialidadeDTO.setEspecialidades(especialidadeEntity.getEspecialidades());
        return especialidadeDTO;
    }

    /**
     * Converte um DTO EspecialidadeProfissionalDTO para uma entidade EspecialidadeProfissionalEntity.
     *
     * @param especialidadeDTO o DTO da especialidade do profissional
     * @return a entidade da especialidade do profissional
     */
    private EspecialidadeProfissionalEntity fromDto(EspecialidadeProfissionalDTO especialidadeDTO) {
        EspecialidadeProfissionalEntity especialidadeEntity = new EspecialidadeProfissionalEntity();
        especialidadeEntity.setEspecialidades(especialidadeDTO.getEspecialidades());
        return especialidadeEntity;
    }

    /**
     * Recupera a agenda de um profissional.
     *
     * @param profissionalId o ID do profissional
     * @return a lista de agendas do profissional
     */
    @Override
    @Transactional
    public List<AgendaDetalhadaDTO> getAgendaByProfissional(Long profissionalId, String statusAgenda, LocalDate data, LocalTime hora, String nomeProfissional) {
        if (!profissionalRepository.existsById(profissionalId)) {
            throw new RegraDeNegocioException("Profissional não encontrado");
        }

        List<AgendaEntity> agendas = agendaRepository.findByProfissionalId(profissionalId);

        return agendas.stream()
                .filter(agenda -> statusAgenda == null || agenda.getStatusAgenda().name().equals(statusAgenda))
                .filter(agenda -> data == null || agenda.getData().equals(data))
                .filter(agenda -> hora == null || agenda.getHora().equals(hora))
                .filter(agenda -> nomeProfissional == null || agenda.getProfissional().getNome().equalsIgnoreCase(nomeProfissional))
                .map(agenda -> {
                    AgendaDetalhadaDTO dto = new AgendaDetalhadaDTO();
                    dto.setCodigoAgenda(agenda.getId());
                    dto.setData(agenda.getData());
                    dto.setHora(agenda.getHora());
                    dto.setPeriodoAgenda(agenda.getJornada().name());
                    dto.setStatusAgenda(agenda.getStatusAgenda().name());
                    dto.setClinica(agenda.getClinicaEntity().getNomeClinica());
                    dto.setEmailClinica(agenda.getClinicaEntity().getEmail());
                    dto.setCelularClinica(agenda.getClinicaEntity().getCelular());
                    dto.setNomeProfissional(agenda.getProfissional().getNome());
                    dto.setNomeEspecialidadeProfissional(String.valueOf(agenda.getEspecialidadeProfissionalEntity().getEspecialidades()));
                    dto.setDescricaoEspecialidadeProfissional(agenda.getEspecialidadeProfissionalEntity().getDescricaoEspecialidade());
                    return dto;
                }).collect(Collectors.toList());
    }
}