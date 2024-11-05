package com.br.TechMed.controller;

import com.br.TechMed.dto.Clinica.EspecialidadeClinicaDTO;
import com.br.TechMed.service.servicos.clinica.EspecialidadeClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/especialidadeClinicas")
public class EspecialidadeClinicaController {

    @Autowired
    private EspecialidadeClinicaService especialidadeClinicaService;

    @PostMapping
    public EspecialidadeClinicaDTO salvarEspecialidade(@RequestBody EspecialidadeClinicaDTO especialidadeClinicaDTO) {
        return especialidadeClinicaService.salvarEspecialidade(especialidadeClinicaDTO);
    }
}
