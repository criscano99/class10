package com.criscano.project.infrastructure.webcontroller;

import com.criscano.project.application.dto.CelularDto;
import com.criscano.project.application.service.CelularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/celulares")
public class CelularController {

    @Autowired
    private CelularService celularService;

    @PostMapping
    public ResponseEntity<CelularDto> guardarCelular(
            @RequestBody CelularDto celularDto
    ) {

        CelularDto celular = celularService.guardar(celularDto);

        return ResponseEntity.ok(celular);
    }

    @GetMapping
    public ResponseEntity<List<CelularDto>> listarCelulares() {

        return ResponseEntity.ok(celularService.listar());
    }
}