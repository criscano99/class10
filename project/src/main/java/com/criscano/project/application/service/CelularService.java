package com.criscano.project.application.service;

import com.criscano.project.application.dto.CelularDto;

import java.util.List;

public interface CelularService {

    CelularDto guardar(CelularDto celularDto);

    List<CelularDto> listar();
}