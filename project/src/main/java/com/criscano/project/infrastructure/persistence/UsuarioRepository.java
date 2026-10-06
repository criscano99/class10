package com.criscano.project.infrastructure.persistence;

import com.criscano.project.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByDeletedFalseOrderByNombreAsc();

    Optional<Usuario> findByIdAndDeletedFalse(Long id);

    Optional<Usuario> findFirstByEmailOrderByIdAsc(String email);
}
