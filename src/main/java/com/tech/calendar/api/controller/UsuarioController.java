package com.tech.calendar.api.controller;

import com.tech.calendar.api.DTO.EventosInscritosDTO;
import com.tech.calendar.api.DTO.IDDTO;
import com.tech.calendar.api.domain.evento.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/eventosInscritos")
    public ResponseEntity<Page<EventosInscritosDTO>> listarEventos(@RequestBody IDDTO idUsuario, Pageable pageable){

        var page = eventoRepository.findByInscritos_Id(idUsuario.id(), pageable).map(EventosInscritosDTO::new);

        return ResponseEntity.ok((Page<EventosInscritosDTO>) page);
    }
}