package com.br.TechMed.utils.utilitaria;

import com.br.TechMed.Enum.Especialidades;
import com.br.TechMed.Enum.StatusAgenda;
import com.br.TechMed.Enum.StatusUsuario;
import com.br.TechMed.dto.response.Profissional.AgendaDetalhadaResponse;
import com.br.TechMed.entity.agenda.AgendaEntity;
import com.br.TechMed.exception.RegraDeNegocioException;
import com.br.TechMed.repository.profissional.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AgendaUtils {

    private final ProfissionalRepository profissionalRepository;



    public static List<AgendaEntity> filterAgendasByProfissional(List<AgendaEntity> agendas, Long profissionalId, ProfissionalRepository profissionalRepository) {
        if (profissionalId != null) {
            if (!profissionalRepository.existsById(profissionalId)) {
                throw new RegraDeNegocioException("Profissional não encontrado");
            }
            return agendas.stream()
                    .filter(agenda -> agenda.getProfissional().getStatusProfissional() == StatusUsuario.ATIVO)
                    .collect(Collectors.toList());
        }
        return agendas;
    }

    public static List<AgendaEntity> filterAgendasByClinica(List<AgendaEntity> agendas, Long clinicaId) {
        return agendas.stream()
                .filter(agenda -> agenda.getClinicaEntity().getId().equals(clinicaId))
                .filter(agenda -> agenda.getProfissional().getStatusProfissional() == StatusUsuario.ATIVO)
                .collect(Collectors.toList());
    }

    public static List<AgendaEntity> filterAgendas(List<AgendaEntity> agendas, LocalDate data, LocalTime hora, String nomeProfissional, String nomeEspecialidade) {
        return agendas.stream()
                .filter(agenda -> agenda.getStatusAgenda() == StatusAgenda.ABERTO || agenda.getStatusAgenda() == StatusAgenda.AGENDADO)
                .filter(agenda -> data == null || agenda.getData().equals(data))
                .filter(agenda -> hora == null || agenda.getHora().equals(hora))
                .filter(agenda -> nomeProfissional == null || agenda.getProfissional().getNome().equalsIgnoreCase(nomeProfissional))
                .filter(agenda -> nomeEspecialidade == null || Especialidades.fromStringIgnoreCase(nomeEspecialidade).equals(agenda.getEspecialidadeProfissionalEntity().getEspecialidades()))
                .collect(Collectors.toList());
    }

    public static AgendaDetalhadaResponse mapToAgendaDetalhadaResponse(AgendaEntity agenda) {
        return new AgendaDetalhadaResponse(
                agenda.getId(),
                agenda.getData(),
                agenda.getHora(),
                agenda.getJornada().name(),
                agenda.getStatusAgenda().name(),
                agenda.getClinicaEntity().getNomeClinica(),
                agenda.getClinicaEntity().getId(),
                agenda.getClinicaEntity().getEmail(),
                agenda.getClinicaEntity().getCelular(),
                agenda.getProfissional().getNome(),
                String.valueOf(agenda.getEspecialidadeProfissionalEntity().getEspecialidades()),
                agenda.getEspecialidadeProfissionalEntity().getDescricaoEspecialidade()
        );
    }
}
