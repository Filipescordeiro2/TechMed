package com.br.clinicaSantana.controller;

import com.br.clinicaSantana.dto.EspecialidadeClinicaDTO;
import com.br.clinicaSantana.service.EspecialidadeClinicaService;
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
