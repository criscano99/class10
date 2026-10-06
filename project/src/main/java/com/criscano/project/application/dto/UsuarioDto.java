package com.criscano.project.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id;
    private String nombre;
    private String email;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private boolean deleted;
}
