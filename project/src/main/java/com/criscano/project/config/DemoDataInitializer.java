package com.criscano.project.config;

import com.criscano.project.application.dto.UsuarioDto;
import com.criscano.project.application.service.UsuarioService;
import com.criscano.project.domain.Usuario;
import com.criscano.project.infrastructure.persistence.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.demo.enabled", havingValue = "true")
public class DemoDataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Override
    @Transactional
    public void run(String... args) {
        guardarUsuarioActivo("Ana Perez", "ana.demo@example.com");
        guardarUsuarioActivo("Carlos Rojas", "carlos.demo@example.com");
        guardarUsuarioEliminado("Usuario Eliminado", "eliminado.demo@example.com");

        imprimirUsuariosActivos();
    }

    private void guardarUsuarioActivo(String nombre, String email) {
        Usuario usuario = usuarioRepository.findFirstByEmailOrderByIdAsc(email)
                .orElseGet(Usuario::new);

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setDeleted(false);
        usuario.setDeletedAt(null);
        usuario.setDeletedBy(null);

        usuarioRepository.save(usuario);
    }

    private void guardarUsuarioEliminado(String nombre, String email) {
        Usuario usuario = usuarioRepository.findFirstByEmailOrderByIdAsc(email)
                .orElseGet(Usuario::new);

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setDeleted(true);
        usuario.setDeletedAt(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        usuario.setDeletedBy("system");

        usuarioRepository.save(usuario);
    }

    private void imprimirUsuariosActivos() {
        List<UsuarioDto> usuariosActivos = usuarioService.listar();

        System.out.println();
        System.out.println("========== USUARIOS ACTIVOS ==========");

        usuariosActivos.forEach(usuario -> System.out.printf(
                "ID: %d | Nombre: %s | Email: %s | Eliminado: %s%n",
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.isDeleted()
        ));

        System.out.println("======================================");
        System.out.println("El usuario eliminado logicamente no aparece en este listado.");
        System.out.println();
    }
}
