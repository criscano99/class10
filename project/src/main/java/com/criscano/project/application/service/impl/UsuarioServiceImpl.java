package com.criscano.project.application.service.impl;

import com.criscano.project.application.dto.UsuarioDto;
import com.criscano.project.application.service.UsuarioService;
import com.criscano.project.domain.Usuario;
import com.criscano.project.infrastructure.persistence.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private static final String DEFAULT_AUDITOR = "system";

    private final UsuarioRepository usuarioRepository;
    private final AuditorAware<String> auditorAware;

    @Override
    public UsuarioDto crear(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());

        return toDto(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> listar() {
        return usuarioRepository.findByDeletedFalseOrderByNombreAsc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto obtenerPorId(Long id) {
        return toDto(buscarUsuarioActivo(id));
    }

    @Override
    public UsuarioDto actualizar(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = buscarUsuarioActivo(id);
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());

        return toDto(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = buscarUsuarioActivo(id);
        usuario.setDeleted(true);
        usuario.setDeletedAt(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        usuario.setDeletedBy(auditorAware.getCurrentAuditor().orElse(DEFAULT_AUDITOR));

        usuarioRepository.save(usuario);
    }

    private Usuario buscarUsuarioActivo(Long id) {
        return usuarioRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private UsuarioDto toDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getCreatedBy(),
                usuario.getUpdatedBy(),
                usuario.getDeletedBy(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt(),
                usuario.getDeletedAt(),
                usuario.isDeleted()
        );
    }
}
