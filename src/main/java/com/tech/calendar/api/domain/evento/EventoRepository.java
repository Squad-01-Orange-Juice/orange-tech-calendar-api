package com.tech.calendar.api.domain.evento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    Page<Evento> findAllByAtivoTrue(Pageable pageable);
}
