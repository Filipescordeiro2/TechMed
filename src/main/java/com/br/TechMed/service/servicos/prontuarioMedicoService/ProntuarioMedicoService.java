package com.br.TechMed.service.servicos.prontuarioMedicoService;

import com.br.TechMed.dto.prontuarioMedico.ExamesClienteDTO;
import com.br.TechMed.dto.prontuarioMedico.MedicamentosClienteDTO;
import com.br.TechMed.dto.prontuarioMedico.ProcedimentosClienteDTO;
import com.br.TechMed.dto.prontuarioMedico.ProtuarioMedicoDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ProntuarioMedicoService {
    ProtuarioMedicoDTO save(ProtuarioMedicoDTO prontuarioMedicoDTO);
    List<ProtuarioMedicoDTO> findByClienteId(Long clienteId);
    long contarExames();
    long contarProcedimentos();
    long contarMedicamentos();

    List<ExamesClienteDTO> findExamesByProfissionalAndDataConsulta(Long profissionalId, LocalDate dataConsulta);
    List<ProcedimentosClienteDTO> findProcedimentosByProfissionalAndDataConsulta(Long profissionalId, LocalDate dataConsulta);
    List<MedicamentosClienteDTO> findMedicamentosByProfissionalAndDataConsulta(Long profissionalId, LocalDate dataConsulta);

    long countExamesByProfissionalAndDataConsultaBetweenAndClinicaId(Long profissionalId, LocalDate startDate, LocalDate endDate, Long clinicaId);
    long countProcedimentosByProfissionalAndDataConsultaBetweenAndClinicaId(Long profissionalId, LocalDate startDate, LocalDate endDate, Long clinicaId);
    long countMedicamentosByProfissionalAndDataConsultaBetweenAndClinicaId(Long profissionalId, LocalDate startDate, LocalDate endDate, Long clinicaId);

}
