package com.criscano.project.application.service;

import com.criscano.project.application.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {

    UsuarioDto crear(UsuarioDto usuarioDto);

    List<UsuarioDto> listar();

    UsuarioDto obtenerPorId(Long id);

    UsuarioDto actualizar(Long id, UsuarioDto usuarioDto);

    void eliminar(Long id);
}
