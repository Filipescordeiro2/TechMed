package com.br.clinicaSantana.controller;


import com.br.clinicaSantana.dto.ClinicaDTO;
import com.br.clinicaSantana.service.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinicas")
public class ClinicaController {

    @Autowired
    private ClinicaService clinicaService;

    @PostMapping
    public ClinicaDTO cadastrarClinica(@RequestBody ClinicaDTO clinicaDTO) {
        return clinicaService.cadastrarClinica(clinicaDTO);
    }
}
