package com.criscano.project.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CelularDto {

    private Long id;
    private String marca;
    private String modelo;
    private String color;
    private String almacenamiento;
    private Double precio;
}