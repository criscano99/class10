package com.criscano.project.infrastructure.persistence;

import com.criscano.project.domain.Celular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CelularRepository extends JpaRepository<Celular, Long> {

    List<Celular> findByDeletedFalseOrderByMarcaAsc();
}
